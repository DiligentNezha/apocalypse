package com.apocalypse.example.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apocalypse.common.dto.Rest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/14
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
public class OAuth2Controller {
    /**
     * github 客户端Id
     */
    private String clientId = "44f360452b6219454fa3";

    /**
     * github 客户端秘钥
     */
    private String clientSecret = "1f5dce795f3b76b00c937abb450ae64b3b9a06f9";

    /**
     * github 获取令牌路径
     */
    private String accessTokenUrl = "https://github.com/login/oauth/access_token";

    /**
     * github 获取用户信息
     */
    private String acquireUserInfo = "https://api.github.com/user";

    @GetMapping("/github/redirect")
    public Rest<JSONObject> redirect(String code) {
        log.info("Auth code【{}】", code);
        JSONObject params = new JSONObject()
                .fluentPut("client_id", clientId)
                .fluentPut("client_secret", clientSecret)
                .fluentPut("code", code);

        //获取token
        String token = HttpUtil.post(accessTokenUrl, params.toJSONString());
        log.info("token 【{}】", token);
        //access_token=58f2a95a8bddf11527ec70279544c92817849a4a&scope=&token_type=bearer
        HashMap<String, String> map = HttpUtil.decodeParamMap(token, "UTF-8");
        String accessToken = map.get("access_token");

        //获取用户信息
        String userInfo = HttpUtil.createGet(acquireUserInfo).header("Authorization", "token " + accessToken).execute().body();
        return Rest.ok(JSON.parseObject(userInfo));
    }
}
