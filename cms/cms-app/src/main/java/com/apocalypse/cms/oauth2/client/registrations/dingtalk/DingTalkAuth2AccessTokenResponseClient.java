package com.apocalypse.cms.oauth2.client.registrations.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;

import java.util.Set;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/20
 */
public class DingTalkAuth2AccessTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {
    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        ClientRegistration clientRegistration = authorizationGrantRequest.getClientRegistration();
        OAuth2AuthorizationExchange authorizationExchange = authorizationGrantRequest.getAuthorizationExchange();

        Set<String> scopes = authorizationExchange.getAuthorizationRequest().getScopes();

        String tokenUri = clientRegistration.getProviderDetails().getTokenUri();

        DingTalkClient client = new DefaultDingTalkClient(tokenUri);
        OapiGettokenRequest req = new OapiGettokenRequest();
        req.setAppkey("dingoapzefxupbfuywyo");
        req.setAppsecret("R-bBYu5VgYkf9-9lnQD9hOenNANUD7RZiOnaCiPYIbKj6lSHF84ViDvuaUedbfR8");
        req.setHttpMethod("GET");
        OapiGettokenResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        OAuth2AccessToken.TokenType tokenType = OAuth2AccessToken.TokenType.BEARER;



        return OAuth2AccessTokenResponse.withToken(rsp.getAccessToken())
                .tokenType(tokenType)
                .expiresIn(rsp.getExpiresIn())
                .scopes(scopes)
                .build();
    }

}
