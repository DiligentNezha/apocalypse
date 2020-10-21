package com.apocalypse.cms.oauth2.client;

import com.apocalypse.cms.oauth2.client.registrations.dingtalk.DingTalkAuth2UserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/20
 */
public class CompositeOAuth2UserService<R extends OAuth2UserRequest, U extends OAuth2User> implements OAuth2UserService<R, U> {

    private final Map<String, OAuth2UserService<? extends OAuth2UserRequest, ? extends OAuth2User>> customUserServices = new HashMap<>();

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> defaultUserService;

    public CompositeOAuth2UserService() {
        defaultUserService = new DefaultOAuth2UserService();
        customUserServices.put(OAuth2RegistrationIds.DING_TALK, new DingTalkAuth2UserService());
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2User oAuth2User;
        if (customUserServices.containsKey(registrationId)) {
            oAuth2User = ((OAuth2UserService) customUserServices.get(registrationId)).loadUser(userRequest);
        } else {
            oAuth2User = defaultUserService.loadUser(userRequest);
        }
        return oAuth2User;
    }


}
