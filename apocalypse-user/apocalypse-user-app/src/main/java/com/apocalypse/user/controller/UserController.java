package com.apocalypse.user.controller;

import com.apocalypse.common.dto.Rest;
import com.apocalypse.user.dto.UserRegisterDTO;
import com.apocalypse.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户服务", tags = {"用户"}, consumes = "application/json")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "用户注册", produces = "application/json")
    public Rest<Long> register(@Validated @RequestBody UserRegisterDTO register) {
        Rest<Long> rest = new Rest<>();
        Long userId = userService.register(register);
        rest.setData(userId);
        return rest;
    }

}
