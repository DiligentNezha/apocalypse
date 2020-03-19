package com.apocalypse.example.geek.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.apocalypse.common.dto.Rest;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.HttpCookie;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/3/12
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "用户登录", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<List<HttpCookie>> login(HttpServletResponse response) {
        List<HttpCookie> cookies = HttpUtil.createPost("https://account.geekbang.org/account/ticket/login")
                .header("Host", "account.geekbang.org")
                .header("Origin", "https://account.geekbang.org")
                .body(new JSONObject()
                        .fluentPut("country", 86)
                        .fluentPut("cellphone", "17765823217")
                        .fluentPut("password", "QQqq499508968")
                        .fluentPut("platform", 3)
                        .fluentPut("appid", 1)
                        .toString())
                .execute()
                .getCookies()
                .stream()
                .filter(cookie -> Arrays.asList("GCID", "GCESS").contains(cookie.getName()))
                .collect(Collectors.toList());
        return Rest.ok(cookies);
    }

    public static void main(String[] args) {
        String cookies = HttpUtil.createPost("https://account.geekbang.org/account/ticket/login")
                .header("Host", "account.geekbang.org")
                .header("Origin", "https://account.geekbang.org")
                .body(new JSONObject()
                        .fluentPut("country", 86)
                        .fluentPut("cellphone", "17765823217")
                        .fluentPut("password", "QQqq499508968")
                        .fluentPut("platform", 3)
                        .fluentPut("appid", 1)
                        .toString())
                .execute()
//                .body();
//        System.out.println(body);
                .getCookies().stream()
                .filter(cookie -> Arrays.asList("GCID", "GCESS").contains(cookie.getName()))
                .map(HttpCookie::toString)
                .collect(Collectors.joining(";"));

        System.out.println(cookies);
        String body = HttpUtil.createPost("https://time.geekbang.org/serv/v1/my/products/all")
                .cookie(cookies)
                .header("Host", "time.geekbang.org")
                .header("Origin", "https://account.geekbang.org")
//                .header("Referer", "https://account.geekbang.org/dashboard/buy")
                .execute()
                .body();
        System.out.println(JSONObject.toJSONString(JSONObject.parseObject(body), true));
    }

}
