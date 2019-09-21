package com.apocalypse.uac.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/9/20
 */
@Slf4j
@Primary
@Component
public class CustomUserDetailsManager implements UserDetailsManager {

    private static Map<String, UserDetails> users = new HashMap<>();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        users.put("root", User.withUsername("root").password(passwordEncoder.encode("root")).authorities("ADMIN",
                "USER").build());
    }

    @Override
    public void createUser(UserDetails user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public void updateUser(UserDetails user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public void deleteUser(String username) {
        users.remove(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.get(username);
    }
}
