package com.apocalypse.example.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.common.util.json.JsonUtil;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptException;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/call")
@Api(value = "OAuth2 回调", tags = {"OAuth2 回调"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class OAuthCallBackController {

    @Autowired
    private RedissonClient redissonClient;

    @PostMapping("/dingtalk/event/test")
    @ApiOperation(value = "钉钉回调 URL 测试", notes = "钉钉回调 URL 测试", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object testDingTalkURL(@RequestParam(value = "signature") String signature,
                                              @RequestParam(value = "timestamp") Long timestamp,
                                              @RequestParam(value = "nonce") String nonce,
                                              @RequestBody(required = false) ObjectNode body) {
        String params = "signature:" + signature + " timestamp:" + timestamp + " nonce:" + nonce + " body:" + body;
        try {
            log.info("begin callback:" + params);

            String aesKey = "T0P6ha3AOyzdHmcPpUwB3vBCsoDHi5HkKtyKl4dKeYt";
            String token = "SFrVFdqSxAkhlCFzEDHbSOGCMuELQuo";
            String suitKey = "suitegpidv8tlhpz1xzua";




            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(token, aesKey, suitKey);

            // 从post请求的body中获取回调信息的加密数据进行解密处理
            String encrypt = body.get("encrypt").asText();
            String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp.toString(), nonce, encrypt);
            ObjectNode callBackContent = JsonUtil.toObj(plainText, ObjectNode.class);

            // 根据回调事件类型做不同的业务处理
            String eventType = callBackContent.get("EventType").asText();

//            SecureUtil.aes(aesKey.getBytes()).encryptBase64()

            // 返回success的加密信息表示回调处理成功
            return dingTalkEncryptor.getEncryptedMap("success", timestamp, nonce);
        } catch (Exception e) {
            //失败的情况，应用的开发者应该通过告警感知，并干预修复
            log.error("process callback fail." + params, e);
            return "fail";
        }
    }

    public static void main(String[] args) throws DingTalkEncryptException {
        String aesKey = "T0P6ha3AOyzdHmcPpUwB3vBCsoDHi5HkKtyKl4dKeYt";
        String token = "SFrVFdqSxAkhlCFzEDHbSOGCMuELQuo";
        String suitKey = "suitegpidv8tlhpz1xzua";

        String signature = "7648e6c722fd5f95e20bb00b1b5086359cd2a605";
        Long timestamp = 1620419204123L;
        String nonce = "gX0RPc1m";
        ObjectNode body = JsonUtil.toObj("{\"encrypt\":\"cZTmHUdyRBuEy3ELb6H3ZaFFMJEf5SqkaxqEjWr8RLgizqHmqwJ8WsS5A9hPtYfrdQG392jBf4KsCzNHo+mVeV2Mn1q2YVSrbGPkc+U7V8/70ZVfTgI5IqdN/xN3ESCrzw/tUq9g16gSKVjEHnlSpMCcLZbS9GcM+P00A6SpKXik09qlXnyowPVPlWuAXopLC8+OxvFgBJDWVN6R60CI4pJt9a+cP09OB70bYrlim0teyjOpCX19aZQDTWuDOlZpR4xQUUAoTUanMwbeesSZqWVV1C9IoE8t1FfXlOmuly0sVPgPsESyKKyDs3GKBsJMMJDtFm8JDaKcfYq9g6jv7A==\"}", ObjectNode.class);



        DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(token, aesKey, suitKey);

        // 从post请求的body中获取回调信息的加密数据进行解密处理
        String encrypt = body.get("encrypt").asText();
        String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp.toString(), nonce, encrypt);
        ObjectNode callBackContent = JsonUtil.toObj(plainText, ObjectNode.class);
        // 根据回调事件类型做不同的业务处理
        System.out.println(callBackContent.toPrettyString());
    }

    @PostMapping("/jxyun/event/test")
    @ApiOperation(value = "健信云回调 URL 测试", notes = "健信云回调 URL 测试", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object testJxYunURL(@RequestParam(value = "signature") String signature,
                                  @RequestParam(value = "timestamp") Long timestamp,
                                  @RequestParam(value = "nonce") String nonce,
                                  @RequestBody(required = false) ObjectNode body) {
        String params = "signature:" + signature + " timestamp:" + timestamp + " nonce:" + nonce + " body:" + body;
        try {
            log.info("begin callback:" + params);

            String aesKey = "T0P6ha3AOyzdHmcPpUwB3vBCsoDHi5HkKtyKl4dKeYt";
            String token = "SFrVFdqSxAkhlCFzEDHbSOGCMuELQuo";
            String suitKey = "swnNZbnXfAOxOpokbFEDdercFp9UZwZI";


            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(token, aesKey, suitKey);

            // 从post请求的body中获取回调信息的加密数据进行解密处理
            String encrypt = body.get("encrypt").asText();
            String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp.toString(), nonce, encrypt);
            ObjectNode callBackContent = JsonUtil.toObj(plainText, ObjectNode.class);

            // 根据回调事件类型做不同的业务处理
            String eventType = callBackContent.get("EventType").asText();

//            SecureUtil.aes(aesKey.getBytes()).encryptBase64()

            // 返回success的加密信息表示回调处理成功
            return dingTalkEncryptor.getEncryptedMap("success", timestamp, nonce);
        } catch (Exception e) {
            //失败的情况，应用的开发者应该通过告警感知，并干预修复
            log.error("process callback fail." + params, e);
            return "fail";
        }
    }

    @GetMapping("/{action}/oauth2/code/app1")
    @ApiOperation(value = "OAuth2 授权码流程回调(应用 1)", notes = "OAuth2 授权码流程回调", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> call1(@PathVariable String action, @RequestParam String code, @RequestParam String state) {
        log.info(StrUtil.format("action[{}],code[{}],state:[{}]", action, code, state));
        String accessToken = null;
//        accessToken = acquireAccessTokenWithClientCredentials("oFBgl2GLucPt84oO7ZeNvUBxP0SY5Esu", "wRwWAG6FOTzMJCkCZPVtA3FVIcc2ohUrVyPPc1XXxVJTetFoxLfgF9oXdlA7IFvJ");
        accessToken = acquireTokenWithAuthenticationCode("oFBgl2GLucPt84oO7ZeNvUBxP0SY5Esu", "wRwWAG6FOTzMJCkCZPVtA3FVIcc2ohUrVyPPc1XXxVJTetFoxLfgF9oXdlA7IFvJ", code, state);

        String identityInfo = identityInfo = getIdentityInfo(accessToken);

        ObjectNode result = JsonUtil.emptyObjectNode();
        result.put("accessToken", accessToken);
        result.put("code", code);
        result.put("state", state);
        ObjectNode identityNode = JsonUtil.toObj(identityInfo, ObjectNode.class).with("data");
        result.putPOJO("identityInfo", identityNode);

        String identityDetail = getIdentityDetailByUnionId(accessToken, identityNode.get("unionId").asText());
        ObjectNode identityDetailNode = JsonUtil.toObj(identityDetail, ObjectNode.class).with("data");
        result.putPOJO("identityDetail", identityDetailNode);
        System.out.println(result.toPrettyString());

        return Rest.vector("result", result, result.getClass());
    }


    @GetMapping("/{action}/oauth2/code/app2")
    @ApiOperation(value = "OAuth2 授权码流程回调(应用 2)", notes = "OAuth2 授权码流程回调", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> call2(@PathVariable String action, @RequestParam String code, @RequestParam String state) {
        log.info(StrUtil.format("action[{}],code[{}],state:[{}]", action, code, state));
        String accessToken = null;

//        accessToken = acquireTokenWithClientCredentials("qKfNRiNK3tesCHoimSlXzO1coLkvcqA7", "MokSiCZh0GIJXNS6wsowEQ9sxa1ojjl5hhGaeO8E1LHrp7jwiZu4PTcSpPoXsTN8");
        accessToken = acquireTokenWithAuthenticationCode("4o1O5CLssxQr1Id3SzOXuOSAZOIPL6k8", "UvxSMZxz7UjGr36wUcRbHm4OqdJAmMtsyYAefQEGHOWYNTw7cyE9PjXFkl3yHPTg", code, state);

        String identityInfo = getIdentityInfo(accessToken);

        ObjectNode result = JsonUtil.emptyObjectNode();
        result.put("accessToken", accessToken);
        result.put("code", code);
        result.put("state", state);
        ObjectNode identityNode = JsonUtil.toObj(identityInfo, ObjectNode.class).with("data");
        result.putPOJO("identityInfo", identityNode);

        String identityDetail = getIdentityDetailByUnionId(accessToken, identityNode.get("unionId").asText());
        ObjectNode identityDetailNode = JsonUtil.toObj(identityDetail, ObjectNode.class).with("data");
        result.putPOJO("identityDetail", identityDetailNode);
        System.out.println(result.toPrettyString());

        return Rest.vector("result", result, result.getClass());
    }

    @GetMapping("/token/inner/acquire")
    @ApiOperation(value = "获取 accessToken", notes = "获取 accessToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> acquireToken(String appKey, String appSecret) {
        String accessToken = acquireAccessTokenWithClientCredentials(appKey, appSecret);

        return Rest.vector("accessToken", accessToken, String.class);
    }

    @GetMapping("/token/suite/acquire")
    @ApiOperation(value = "获取 suiteAccessToken", notes = "获取 suiteAccessToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> acquireSuiteToken(String suiteKey, String suiteSecret, String suiteTicket) {
        String accessToken = acquireSuiteTokenWithClientCredentials(suiteKey, suiteSecret, suiteTicket);

        return Rest.vector("suiteAccessToken", accessToken, String.class);
    }

    @GetMapping("/token/corp/acquire")
    @ApiOperation(value = "获取 corpAccessToken", notes = "获取 corpAccessToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> acquireCorpToken(String suiteKey, String suiteSecret, String suiteTicket, String authCorpCode) {
        String accessToken = acquireCorpTokenWithClientCredentials(suiteKey, suiteSecret, suiteTicket, authCorpCode);

        return Rest.vector("corpAccessToken", accessToken, String.class);
    }

    private String acquireAccessTokenWithClientCredentials(String appKey, String appSecret) {
        Map map = new HashMap();
//        map.put("grant_type", "client_credentials");
        map.put("appKey", appKey);
        map.put("appSecret", appSecret);
        String bodyJson = HttpUtil.createPost("http://ids.api.gkjxhz.tech/oauth2/accessToken")
//                .basicAuth(appKey, appSecret)
                .form(map)
                .execute()
                .body();
        JsonNode jsonNode = JsonUtil.toObj(bodyJson, JsonNode.class);
        return jsonNode.get("access_token").asText();
    }

    private String acquireCorpTokenWithClientCredentials(String suiteKey, String suiteSecret, String suiteTicket, String authCorpCode) {
        Map map = new HashMap();
//        map.put("grant_type", "client_credentials");
        map.put("suiteKey", suiteKey);
        map.put("suiteSecret", suiteSecret);
        map.put("suiteTicket", suiteTicket);
        map.put("authCorpCode", authCorpCode);
        String bodyJson = HttpUtil.createPost("http://ids.api.gkjxhz.tech/oauth2/corpAccessToken")
//                .basicAuth(appKey, appSecret)
                .form(map)
                .execute()
                .body();
        JsonNode jsonNode = JsonUtil.toObj(bodyJson, JsonNode.class);
        return jsonNode.get("access_token").asText();
    }

    private String acquireSuiteTokenWithClientCredentials(String suiteKey, String suiteSecret, String suiteTicket) {
        Map map = new HashMap();
//        map.put("grant_type", "client_credentials");
        map.put("suiteKey", suiteKey);
        map.put("suiteSecret", suiteSecret);
        map.put("suiteTicket", suiteTicket);
        String bodyJson = HttpUtil.createPost("http://ids.api.gkjxhz.tech/oauth2/suiteAccessToken")
                .basicAuth(suiteKey, suiteSecret)
                .form(map)
                .execute()
                .body();
        JsonNode jsonNode = JsonUtil.toObj(bodyJson, JsonNode.class);
        return jsonNode.get("access_token").asText();
    }

    private String acquireTokenWithAuthenticationCode(String appKey, String appSecret, String code, String state) {
        Map map = new HashMap();
        map.put("grant_type", "authorization_code");
        map.put("code", code);
        map.put("appKey", appKey);
        map.put("appSecret", appSecret);
        // 不传跳默认注册的跳转路径
//        map.put("redirect_url", state);
        String bodyJson = HttpUtil.createPost("http://ids.api.gkjxhz.tech/oauth2/userAccessToken")
//                .basicAuth(appKey, appSecret)
                .form(map)
                .execute()
                .body();
        JsonNode jsonNode = JsonUtil.toObj(bodyJson, JsonNode.class);
        return jsonNode.get("access_token").asText();
    }

    private String getIdentityInfo(String accessToken) {
        String bodyJson = HttpUtil.createGet("http://open.api.gkjxhz.tech/identity/profile")
                .header("Authorization", "Bearer " + accessToken)
                .execute()
                .body();
        return bodyJson;
    }

    private String getIdentityDetailByUnionId(String accessToken, String unionId) {
        String bodyJson = HttpUtil.createGet("http://open.api.gkjxhz.tech/identity/detail/unionid/" + unionId)
                .header("Authorization", "Bearer " + accessToken)
                .execute()
                .body();
        return bodyJson;
    }

}


