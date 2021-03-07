package com.apocalypse.cms.controller;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.apocalypse.cms.api.response.OAuth2MeResponse;
import com.apocalypse.cms.api.response.OAuth2MobileResponse;
import com.apocalypse.cms.oauth2.client.OAuth2RegistrationIds;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.common.util.json.JsonUtil;
import com.apocalypse.idaas.api.request.AdminLoginRequest;
import com.apocalypse.idaas.api.response.LoginResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

@Validated
@RestController
@RequestMapping
@Api(value = "认证", tags = {"认证相关"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {


    public static final String MOBILE_GET_URL = "http://localhost:31001/open/mobile";
    public static final String MOBILE_ALONE_GET_URL = "http://localhost:32001/open/mobile";

    @ApiOperation(value = "登录", notes = "员工登录", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<LoginResponse> fakeLogin(@Validated AdminLoginRequest request) {
        throw new IllegalArgumentException("Add Spring Security to handle authentication");
    }

    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping("/me/authorized/client")
    @ApiOperation(value = "以授权的个人信息", notes = "以授权的个人信息", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> me(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        return Rest.vector("authorizedClient", authorizedClient, authorizedClient.getClass());
    }

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

}


