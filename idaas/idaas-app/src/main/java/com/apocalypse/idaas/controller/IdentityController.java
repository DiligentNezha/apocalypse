package com.apocalypse.idaas.controller;

import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.idaas.module.single.Identity;
import com.apocalypse.idaas.service.single.ElementService;
import com.apocalypse.idaas.service.single.IdentityService;
import com.apocalypse.idaas.service.single.StaffService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/identity")
@Api(value = "身份", tags = {"用户身份"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class IdentityController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ElementService elementService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private StaffService staffService;

    @GetMapping("/me")
    @ApiOperation(value = "个人信息", notes = "个人信息", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> me(Authentication authentication) {
        return Rest.vector("principal", authentication.getPrincipal(), authentication.getPrincipal().getClass());
    }

}


