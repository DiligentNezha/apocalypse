package com.apocalypse.cms.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.apocalypse.cms.api.response.OAuth2MeResponse;
import com.apocalypse.cms.api.response.OAuth2MobileResponse;
import com.apocalypse.cms.util.JwtUtil;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.common.util.json.JsonUtil;
import com.apocalypse.idaas.api.request.AdminLoginRequest;
import com.apocalypse.idaas.api.response.LoginResponse;
import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.idsmanager.dingdang.jwt.DingdangUserRetriever;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.Collection;
import java.util.Map;

@Validated
@Controller
@RequestMapping
@Api(value = "认证", tags = {"认证相关"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {


    public static final String MOBILE_GET_URL = "http://localhost:31001/open/mobile";
    public static final String MOBILE_ALONE_GET_URL = "http://localhost:32001/open/mobile";

    @ApiOperation(value = "登录", notes = "员工登录", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<LoginResponse> fakeLogin(@Validated AdminLoginRequest request) {
        throw new IllegalArgumentException("Add Spring Security to handle authentication");
    }

    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping("/login")
    @ApiOperation(value = "进入登录页", notes = "进入登录页", produces = MediaType.APPLICATION_JSON_VALUE)
    public String toLoginPage() {
        return "login";
    }

    @ResponseBody
    @GetMapping("/me/authorized/client")
    @ApiOperation(value = "以授权的个人信息", notes = "以授权的个人信息", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> me(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        return Rest.vector("authorizedClient", authorizedClient, authorizedClient.getClass());
    }

    @ResponseBody
    @GetMapping("/me")
    @ApiOperation(value = "个人信息", notes = "个人信息", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<OAuth2MeResponse> me(OAuth2AuthenticationToken authenticationToken) {
        OAuth2MeResponse response = new OAuth2MeResponse();

        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(authenticationToken.getAuthorizedClientRegistrationId(), authenticationToken.getName());
        String clientName = oAuth2AuthorizedClient.getClientRegistration().getClientName();
        Collection<GrantedAuthority> authorities = authenticationToken.getAuthorities();
        response.setAuthorities(authorities)
                .setAttributes(authenticationToken.getPrincipal().getAttributes())
                .setClientName(clientName);

        return Rest.success(response);
    }

    @ResponseBody
    @GetMapping("/mobile")
    @ApiOperation(value = "获取用户手机号", notes = "获取用户手机号", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<OAuth2MobileResponse> mobile(OAuth2AuthenticationToken authenticationToken) {
        OAuth2MobileResponse response = new OAuth2MobileResponse();

        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(authenticationToken.getAuthorizedClientRegistrationId(), authenticationToken.getName());
        String clientName = oAuth2AuthorizedClient.getClientRegistration().getClientName();

        HttpResponse httpResponse = HttpUtil.createGet(MOBILE_GET_URL + "/" + ((Map) authenticationToken.getPrincipal().getAttribute("identity")).get("id"))
                .header("Authorization", "Bearer " + oAuth2AuthorizedClient.getAccessToken().getTokenValue())
                .execute();

        ObjectNode objectNode = JsonUtil.toObj(httpResponse.body(), ObjectNode.class);
        String mobile = objectNode.get("data").get("mobile").asText();

        response.setMobile(mobile)
                .setClientName(clientName);

        return Rest.success(response);
    }

    @ResponseBody
    @GetMapping("/mobile/alone")
    @ApiOperation(value = "获取用户手机号（独立资源服务器）", notes = "获取用户手机号（独立资源服务器）", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<OAuth2MobileResponse> mobileAlong(OAuth2AuthenticationToken authenticationToken) {
        OAuth2MobileResponse response = new OAuth2MobileResponse();

        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(authenticationToken.getAuthorizedClientRegistrationId(), authenticationToken.getName());
        String clientName = oAuth2AuthorizedClient.getClientRegistration().getClientName();

        HttpResponse httpResponse = HttpUtil.createGet(MOBILE_ALONE_GET_URL + "/" + ((Map) authenticationToken.getPrincipal().getAttribute("identity")).get("id"))
                .header("Authorization", "Bearer " + oAuth2AuthorizedClient.getAccessToken().getTokenValue())
                .execute();

        ObjectNode objectNode = JsonUtil.toObj(httpResponse.body(), ObjectNode.class);
        String mobile = objectNode.get("data").get("mobile").asText();

        response.setMobile(mobile)
                .setClientName(clientName);

        return Rest.success(response);
    }

    @ResponseBody
    @GetMapping("/sso/token/verify/aliyun")
    public String tokenVerifyAliyun(@RequestParam String id_token, @RequestParam String target_url) {
        DingdangUserRetriever retriever = new DingdangUserRetriever(id_token, "{\"kty\":\"RSA\",\"kid\":\"5753343775855434003\",\"alg\":\"RS256\",\"n\":\"sBJ7gfbvq9Y9whL1i6LVEmq1fSCkT61IFlhEIitFjrR8dz-DbRNqAxAec3IAUt-AVXfHBhW-QE5lk7zWY_rBK35b1dbYWnuRwnyyW2mWA4nhjr_wT3i7NxHri6TAMl__166pCyXOu9NGSQvHNgekNS7JMMG_i_UNuGxd3mMOwBaWaz5ms2TIF1Hcypog2j3GMHmjxto5fxieq4xyjH_2jTxmP1895LpdQ1Z9aGeUL1O26WO1TEfateQmsIg0FKQgalZFJUpxDiLcvSO-DK4qJsXu5IzzXA-qC5qVuQ_kVxkrrsIXO5QvfUmNKYES_IDhFEFG2WcA1qgBqAAIgxH43w\",\"e\":\"AQAB\"}");
        DingdangUserRetriever.User user = null;
        System.out.println(target_url);

        try {
            user = retriever.retrieve();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        if ("root".equals(user.getUsername())) {
            System.out.println(user);
            return "/doc.html";
        } else {
            return "404";
        }
    }

    @ResponseBody
    @GetMapping("/sso/token/verify/jianxinyun")
    public String tokenVerifyJianXinYun(@RequestParam String id_token, @RequestParam String target_url) {
        Map<String, Claim> userMap = null;
        try {
            userMap = JwtUtil.verifyToken(id_token);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        if ("root".equals(userMap.get("userName").asString())) {
            return "/doc.html";
        } else {
            return "404";
        }
    }

    @ResponseBody
    public static void main(String[] args) {
        PublicKey publicKey = SecureUtil.rsa().getPublicKey();
        System.out.println(JsonUtil.toJsonStr(publicKey));
//        System.out.println(publicKey);

    }
}


