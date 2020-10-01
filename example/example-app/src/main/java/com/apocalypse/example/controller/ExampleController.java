package com.apocalypse.example.controller;

import cn.hutool.core.util.RandomUtil;
import com.apocalypse.common.constraints.EnumMatch;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.enums.GenderEnum;
import com.apocalypse.uac.dto.UserRegisterDTO;
import com.apocalypse.uac.feign.UserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/example")
@Api(value = "Example", tags = {"Example"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ExampleController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/register")
    @ApiOperation(value = "注册", notes = "用户注册", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<Long> register() {
        int randomInt = RandomUtil.randomInt(1, 10);
        UserRegisterDTO userRegister = new UserRegisterDTO()
                .setNickname("机智的小哪吒" + randomInt)
                .setPhone("1776582321" + randomInt);
        return userClient.register(userRegister);
    }

    @PostMapping("/enum/post")
    @ApiOperation(value = "枚举校验(RequestBody)", notes = "扩展枚举校验", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<EnumRequest> enumPostValidate(@Validated @RequestBody EnumRequest request) {
        return Rest.ok(request);
    }

    @PostMapping("/enum/get")
    @ApiOperation(value = "枚举校验(RequestParam)", notes = "扩展枚举校验", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<String> enumGetValidate(@Valid @EnumMatch(GenderEnum.class) @RequestParam("gender") String gender) {
        return Rest.ok(gender);
    }

//    @Autowired
//    private NodeServiceClient nodeServiceClient;

    @PostMapping("/sidecar/node")
    @ApiOperation(value = "异构 node", notes = "异构 node 测试", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<Rest> enumGetValidate() {
//        Rest hello = nodeServiceClient.hello();
//        log.info(JSON.toJSONString(hello));
        return Rest.ok(null);
    }

    @Getter
    @Setter
    public static class EnumRequest {

        @EnumMatch(GenderEnum.class)
        @ApiModelProperty(value = "性别索引", example = "1")
        private Integer genderIndex;

        @EnumMatch(GenderEnum.class)
        private String gender;
    }

}
