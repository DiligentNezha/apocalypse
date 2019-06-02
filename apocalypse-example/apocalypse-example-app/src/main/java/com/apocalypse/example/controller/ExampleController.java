package com.apocalypse.example.controller;

import cn.hutool.core.util.RandomUtil;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.user.client.UserClient;
import com.apocalypse.user.dto.UserRegisterDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/example")
@Api(value = "Example", tags = {"Example"}, consumes = "application/json")
public class ExampleController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/register")
    @ApiOperation(value = "注册", notes = "用户注册", produces = "application/json")
    public Rest<Long> register() {
        Rest<Long> rest = new Rest<>();
        int randomInt = RandomUtil.randomInt(1, 10);
        UserRegisterDTO userRegister = new UserRegisterDTO()
                .setNickname("机智的小哪吒" + randomInt)
                .setPhone("1776582321" + randomInt);
        return userClient.register(userRegister);
    }
}
