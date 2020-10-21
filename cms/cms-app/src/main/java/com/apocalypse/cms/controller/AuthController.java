package com.apocalypse.cms.controller;

import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.idaas.api.request.AdminLoginRequest;
import com.apocalypse.idaas.api.response.LoginResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Validated
@RestController
@RequestMapping
@Api(value = "认证", tags = {"认证相关"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private RedissonClient redissonClient;

    @ApiOperation(value = "登录", notes = "员工登录", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<LoginResponse> fakeLogin(@Validated AdminLoginRequest request) {
        throw new IllegalArgumentException("Add Spring Security to handle authentication");
    }


    @GetMapping("/me")
    @ApiOperation(value = "个人信息", notes = "个人信息", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> me(Principal principal, Authentication authentication, @AuthenticationPrincipal AuthenticatedPrincipal authenticatedPrincipal) {
        return Rest.vector("principal", authenticatedPrincipal, authenticatedPrincipal.getClass());
    }

}


