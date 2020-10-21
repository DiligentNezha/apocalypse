package com.apocalypse.cms.oauth2.client.registrations.dingtalk;

import cn.hutool.core.util.ObjectUtil;
import com.apocalypse.common.boot.util.HttpContextUtil;
import com.apocalypse.common.util.json.JsonUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetUseridByUnionidRequest;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetUseridByUnionidResponse;
import com.taobao.api.ApiException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/20
 */
public class DingTalkAuth2UserService implements OAuth2UserService<OAuth2UserRequest, DingTalkOAuth2User> {
    @Override
    public DingTalkOAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
        HttpServletRequest httpServletRequest = HttpContextUtil.getHttpServletRequest();
        OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
        req.setTmpAuthCode(httpServletRequest.getParameter("code"));
        DingTalkOAuth2User dingTalkOAuth2User = new DingTalkOAuth2User();
        try {
            OapiSnsGetuserinfoBycodeResponse response = client.execute(req,"dingoav6meuvwoo0hep7mm","g47vFUz9AwpiAXWhnpJN3faY2XF7CXq4UVXHP1KySjwOx1D2LTn-nsRvQkm7uASw");
            OapiSnsGetuserinfoBycodeResponse.UserInfo userInfo = response.getUserInfo();

            if (ObjectUtil.isNotNull(userInfo)) {
                dingTalkOAuth2User.setNick(userInfo.getNick());
                dingTalkOAuth2User.setOpenId(userInfo.getOpenid());
                dingTalkOAuth2User.setUnionId(userInfo.getUnionid());

                client.resetServerUrl("https://oapi.dingtalk.com/user/getUseridByUnionid");
                OapiUserGetUseridByUnionidRequest userIdRequest = new OapiUserGetUseridByUnionidRequest();
                userIdRequest.setUnionid(userInfo.getUnionid());
                userIdRequest.setHttpMethod("GET");
                OapiUserGetUseridByUnionidResponse userIdResponse = client.execute(userIdRequest, userRequest.getAccessToken().getTokenValue());

                client.resetServerUrl("https://oapi.dingtalk.com/user/get");
                OapiUserGetRequest userGetRequest = new OapiUserGetRequest();
                userGetRequest.setUserid(userIdResponse.getUserid());
                userGetRequest.setHttpMethod("GET");
                OapiUserGetResponse userGetResponse = client.execute(userGetRequest, userRequest.getAccessToken().getTokenValue());

                dingTalkOAuth2User.getAttributes().put("attach", JsonUtil.toJsonStr(userGetResponse));


            } else {
                userInfo.setNick("不知道");
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return dingTalkOAuth2User;
    }
}
