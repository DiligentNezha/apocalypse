package com.apocalypse.uac.config.security;

import com.apocalypse.uac.convert.UserConvert;
import com.apocalypse.uac.model.UserDO;
import com.apocalypse.uac.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/9/20
 */
@Slf4j
@Primary
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private UserConvert userConvert;

    @PostConstruct
    public void init() {
        if (!userService.existsWithProperty(UserDO::getUsername, "root")) {
            UserDO userDO = new UserDO()
                    .setUsername("root")
                    .setPassword(passwordEncoder.encode("root"))
                    .setNickname("江阿生")
                    .setPhone("177xxxx3217");
            userService.insertSelective(userDO);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO user = userService.selectOneByProperty(UserDO::getUsername, username);
        if (Objects.nonNull(user)) {
            return userConvert.convert(user);
        } else {
            return null;
        }
    }
}
