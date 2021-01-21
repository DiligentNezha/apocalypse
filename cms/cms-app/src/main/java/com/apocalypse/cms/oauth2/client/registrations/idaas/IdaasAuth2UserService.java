package com.apocalypse.cms.oauth2.client.registrations.idaas;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.druid.sql.visitor.functions.If;
import com.apocalypse.cms.oauth2.client.registrations.dingtalk.DingTalkOAuth2User;
import com.apocalypse.common.boot.util.HttpContextUtil;
import com.apocalypse.common.util.json.JsonUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetUseridByUnionidRequest;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetUseridByUnionidResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.taobao.api.ApiException;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/20
 */
public class IdaasAuth2UserService implements OAuth2UserService<OAuth2UserRequest, IdaasOAuth2User> {

    private static final String MISSING_USER_INFO_URI_ERROR_CODE = "missing_user_info_uri";

    private static final String MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE = "missing_user_name_attribute";

    private static final String INVALID_USER_INFO_RESPONSE_ERROR_CODE = "invalid_user_info_response";

    @Override
    public IdaasOAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String uri = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();

        String body = HttpUtil.createGet(uri).header("Authorization", "Bearer " + userRequest.getAccessToken().getTokenValue()).execute().body();
        ObjectNode response = JsonUtil.toObj(body, ObjectNode.class);

        JsonNode principal = response.get("data").get("principal");

        IdaasOAuth2User idaasOAuth2User = new IdaasOAuth2User();
        idaasOAuth2User.setUsername(principal.get("identity").get("loginName").asText());
        idaasOAuth2User.setEnabled(principal.get("enabled").asBoolean());
        idaasOAuth2User.setAccountNonExpired(principal.get("accountNonExpired").asBoolean());
        idaasOAuth2User.setAccountNonLocked(principal.get("accountNonLocked").asBoolean());
        idaasOAuth2User.setCredentialsNonExpired(principal.get("credentialsNonExpired").asBoolean());
        idaasOAuth2User.setAttributes(toMap(principal));
        return idaasOAuth2User;
    }

    private LinkedHashMap<String, Object> toMap(JsonNode jsonNode) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        jsonNode.fieldNames().forEachRemaining(fieldName -> {
            JsonNode nodeValue = jsonNode.get(fieldName);
            if (nodeValue.isObject()) {
                LinkedHashMap<String, Object> nodeValueToMap = toMap(nodeValue);
                map.put(fieldName, nodeValueToMap);
            } else if (nodeValue.isArray()) {
                ArrayList list = new ArrayList();
                nodeValue.elements().forEachRemaining(element -> {
                    LinkedHashMap<String, Object> elementMap = toMap(element);
                    list.add(elementMap);
                });
                map.put(fieldName, list);
            } else {
                map.put(fieldName, jsonNode.get(fieldName));
            }
        });
        return map;
    }
}
