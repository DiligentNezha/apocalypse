package com.gkjx.saas.health.admin.config.security.authentication;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gkjx.common.boot.util.HttpContextUtil;
import com.gkjx.common.util.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/7/15
 */
@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ObjectNode data = JsonUtil.emptyObjectNode();

        HttpContextUtil.write(response, JsonUtil.defaultObjectMapper()
                .createObjectNode()
                .put("code", "00000")
                .put("msg", "退出成功")
                .putPOJO("data", data));
    }
}
