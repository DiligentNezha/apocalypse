package com.apocalypse.cms.oauth2.client;

import com.apocalypse.cms.oauth2.client.registrations.dingtalk.DingTalkAuth2AccessTokenResponseClient;
import com.apocalypse.cms.oauth2.client.registrations.dingtalk.DingTalkAuth2UserService;
import com.apocalypse.cms.oauth2.client.registrations.dingtalk.DingTalkOAuth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.util.UriBuilder;
import tk.mybatis.mapper.weekend.Fn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/20
 */
@EnableWebSecurity(debug = true)
public class OAuth2ClientConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login/oauth2")
                .permitAll()
                .anyRequest()
                .authenticated();

        http.oauth2Login()
                .defaultSuccessUrl("/me")
                .tokenEndpoint()
                .accessTokenResponseClient(accessTokenResponseClient())
                .and()
                .userInfoEndpoint()
                .customUserType(DingTalkOAuth2User.class, OAuth2RegistrationIds.DING_TALK)
                .userService(oAuth2UserService())
                .and()
                .redirectionEndpoint()
                .baseUri("/call/**")
                .and()
                .authorizationEndpoint()
                .authorizationRequestResolver(auth2AuthorizationRequestResolver());

    }

    @Bean
    public OAuth2AuthorizationRequestResolver auth2AuthorizationRequestResolver() {
        DefaultOAuth2AuthorizationRequestResolver defaultOAuth2AuthorizationRequestResolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, DEFAULT_AUTHORIZATION_REQUEST_BASE_URI);
        defaultOAuth2AuthorizationRequestResolver.setAuthorizationRequestCustomizer(builder -> {
            builder.authorizationRequestUri(new Fn<UriBuilder, URI>() {
                @Override
                public URI apply(UriBuilder uriBuilder) {
                    URI uri = uriBuilder.build();
                    if (uri.getHost().contains("dingtalk")) {
                        String replace = uri.toString().replace("client_id", "appid");
                        try {
                            uri = new URI(replace);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                    return uri;
                }
            });
        });
        return defaultOAuth2AuthorizationRequestResolver;
    }


    private OAuth2AccessTokenResponseClient accessTokenResponseClient() {
        CompositeOAuth2AccessTokenResponseClient accessTokenResponseClient = new CompositeOAuth2AccessTokenResponseClient();
        accessTokenResponseClient.getCustomClients().put(OAuth2RegistrationIds.DING_TALK, new DingTalkAuth2AccessTokenResponseClient());
        return accessTokenResponseClient;
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        CompositeOAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new CompositeOAuth2UserService<>();
        return oAuth2UserService;
    }

}
