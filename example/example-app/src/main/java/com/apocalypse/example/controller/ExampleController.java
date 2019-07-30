package com.apocalypse.example.controller;

import cn.hutool.core.util.RandomUtil;
import com.apocalypse.common.constraints.EnumMatch;
import com.apocalypse.common.constraints.ValuesReadable;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.enums.GenderEnum;
import com.apocalypse.user.client.UserClient;
import com.apocalypse.user.dto.UserRegisterDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        int randomInt = RandomUtil.randomInt(1, 10);
        UserRegisterDTO userRegister = new UserRegisterDTO()
                .setNickname("机智的小哪吒" + randomInt)
                .setPhone("1776582321" + randomInt);
        return userClient.register(userRegister);
    }

    @PostMapping("/enum")
    @ApiOperation(value = "枚举校验", notes = "扩展枚举校验", produces = "application/json")
    public Rest<EnumRequest> enumValidate(@Validated @RequestBody EnumRequest request) {
        return Rest.ok(request);
    }

    @Getter
    @Setter
    public static class EnumRequest {

        @EnumMatch(GenderEnum.class)
        @ApiModelProperty(value = "性别索引", example = "1")
        private Integer genderIndex;

        @EnumMatch(GenderEnum.class)
        @ApiModelProperty(value = "性别", example = "男")
        private String gender;
    }

}
