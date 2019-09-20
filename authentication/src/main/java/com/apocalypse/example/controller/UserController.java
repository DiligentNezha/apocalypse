package com.apocalypse.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/9/19
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/current")
    public Object current() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/query/{username}")
    public Object query(@PathVariable String username) {
        return userDetailsManager.userExists(username);
    }

    @GetMapping("/create")
    public Object create(@RequestParam String username, @RequestParam String password) {
        userDetailsManager.createUser(User.withUsername(username).password(passwordEncoder.encode(password)).authorities("USER").build());
        return userDetailsManager.userExists(username);
    }


}
