package com.apocalypse.example.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.example.manager.AlipayManager;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/14
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
@Api(value = "OAuth2案例", tags = {"OAuth2案例"}, consumes = "application/json")
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

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AlipayManager alipayManager;

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

    /**
     * 支付宝授权回调地址
     */
    @GetMapping("/auth/alipay/redirect")
    public Rest<JSONObject> alipayRedirect(@RequestParam("app_id") String appId,
                                           @RequestParam("source") String source,
                                           @RequestParam(value = "scope") String scope,
                                           @RequestParam("auth_code") String authCode,
                                           @RequestParam("state") String state) {
        log.info("state【{}】;appId【{}】;source【{}】;scope【{}】;authCode【{}】", state, appId, source, scope, authCode);
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(state, "appId", appId);
        hashOperations.put(state, "source", source);
        hashOperations.put(state, "scope", scope);
        hashOperations.put(state, "authCode", authCode);

        AlipayClient alipayClient = alipayManager.getAlipayClient();
        AlipaySystemOauthTokenRequest alipaySystemOauthTokenRequest = new AlipaySystemOauthTokenRequest();
        alipaySystemOauthTokenRequest.setCode(authCode);
        alipaySystemOauthTokenRequest.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(alipaySystemOauthTokenRequest);
            hashOperations.put(state, "oauthTokenResponse", oauthTokenResponse);
            hashOperations.put(state, "oauthTokenResponseBody", JSON.parseObject(oauthTokenResponse.getBody()));

            AlipayUserInfoShareRequest alipayUserInfoShareRequest = new AlipayUserInfoShareRequest();
            AlipayUserInfoShareResponse alipayUserInfoShareResponse = alipayClient.execute(alipayUserInfoShareRequest, oauthTokenResponse.getAccessToken());
            hashOperations.put(state, "alipayUserInfoShareResponse", alipayUserInfoShareResponse);
            hashOperations.put(state, "alipayUserInfoShareResponseBody",
                    JSON.parseObject(alipayUserInfoShareResponse.getBody()));
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
        }
        return Rest.ok(null);
    }

}
