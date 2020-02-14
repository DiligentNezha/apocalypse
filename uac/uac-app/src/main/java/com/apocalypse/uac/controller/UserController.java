package com.apocalypse.uac.controller;

import com.apocalypse.common.dto.Rest;
import com.apocalypse.uac.dto.UserRegisterDTO;
import com.apocalypse.uac.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户服务", tags = {"用户"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "用户注册", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<Long> register(@Validated @RequestBody UserRegisterDTO register) {
        Rest<Long> rest = new Rest<>();
        Long userId = userService.register(register);
        rest.setData(userId);
        return rest;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/current")
    public Object current() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
