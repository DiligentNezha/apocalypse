package com.apocalypse.idaas.controller;

import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.idaas.api.request.AdminLoginRequest;
import com.apocalypse.idaas.api.response.LoginResponse;
import com.apocalypse.idaas.constants.SecurityConstants;
import com.apocalypse.idaas.service.single.ElementService;
import com.apocalypse.idaas.service.single.IdentityService;
import com.apocalypse.idaas.service.single.StaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@Controller
@RequestMapping
@Api(value = "认证", tags = {"认证相关"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ElementService elementService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private StaffService staffService;

    @GetMapping("/login")
    @ApiOperation(value = "进入登录页", notes = "进入登录页", produces = MediaType.APPLICATION_JSON_VALUE)
    public String toLoginPage() {
        return "login";
    }

    @ResponseBody
    @PostMapping(SecurityConstants.LOGIN_PROCESSING_URL)
    @ApiOperation(value = "登录", notes = "员工登录", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<LoginResponse> fakeLogin(@Validated AdminLoginRequest request) {
        throw new IllegalArgumentException("Add Spring Security to handle authentication");
    }

    @ResponseBody
    @GetMapping(SecurityConstants.AUTH_LOGOUT)
    @ApiOperation(value = "注销", notes = "员工注销", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> fakeLogout() {
        throw new IllegalArgumentException("Add Spring Security to handle authentication");
    }

    @ResponseBody
    @GetMapping("/me")
    @ApiOperation(value = "个人信息", notes = "个人信息", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> me(Authentication authentication, @AuthenticationPrincipal AuthenticatedPrincipal authenticatedPrincipal) {
        return Rest.vector("principal", authentication, authentication.getClass());
    }

}


