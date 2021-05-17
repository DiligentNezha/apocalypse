package com.apocalypse.idaas.controller;

import cn.hutool.core.util.URLUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Validated
@Controller
@RequestMapping
@Api(value = "授权", tags = {"授权相关"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthorizationController {

    @GetMapping("/oauth/authorize/transfer")
    @ApiOperation(value = "授权中转页（前端页面）", notes = "授权中转页（前端页面）", produces = MediaType.APPLICATION_JSON_VALUE)
    public String transfer(HttpServletRequest request, HttpServletResponse response) {
        return "/authorize/transfer";
    }

    @GetMapping("/oauth/authorize/finish/{redirectUrl}")
    @ApiOperation(value = "授权完成（前端页面）", notes = "授权完成（前端页面）", produces = MediaType.APPLICATION_JSON_VALUE)
    public String finish(String redirectUrl) {
        return "/authorize/finish?redirectUrl=" + URLUtil.encodeAll(redirectUrl);
    }

}


