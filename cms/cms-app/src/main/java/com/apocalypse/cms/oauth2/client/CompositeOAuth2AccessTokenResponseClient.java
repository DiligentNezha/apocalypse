package com.apocalypse.cms.oauth2.client;

import com.apocalypse.cms.oauth2.client.registrations.dingtalk.DingTalkAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/20
 */
public class CompositeOAuth2AccessTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    private Map<String, OAuth2AccessTokenResponseClient> customClients = new HashMap<>();

    private Map<AuthorizationGrantType, OAuth2AccessTokenResponseClient> defaultClients;

    public CompositeOAuth2AccessTokenResponseClient(Map<String, OAuth2AccessTokenResponseClient> customClients) {
        this.customClients = customClients;

        this.defaultClients = new HashMap<>();
        this.defaultClients.put(AuthorizationGrantType.AUTHORIZATION_CODE, new DefaultAuthorizationCodeTokenResponseClient());
        this.defaultClients.put(AuthorizationGrantType.CLIENT_CREDENTIALS, new DefaultClientCredentialsTokenResponseClient());
        this.defaultClients.put(AuthorizationGrantType.REFRESH_TOKEN, new DefaultRefreshTokenTokenResponseClient());
        this.defaultClients.put(AuthorizationGrantType.PASSWORD, new DefaultPasswordTokenResponseClient());
    }

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        String clientId = authorizationGrantRequest.getClientRegistration().getRegistrationId();
        OAuth2AccessTokenResponseClient auth2AccessTokenResponseClient;
        if (customClients.containsKey(clientId)) {
            auth2AccessTokenResponseClient = customClients.get(clientId);
        } else {
            AuthorizationGrantType grantType = authorizationGrantRequest.getGrantType();
            auth2AccessTokenResponseClient = this.defaultClients.get(grantType);
        }
        return auth2AccessTokenResponseClient.getTokenResponse(authorizationGrantRequest);
    }

    public Map<String, OAuth2AccessTokenResponseClient> getCustomClients() {
        return customClients;
    }
}
