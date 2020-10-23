package com.apocalypse.idaas.config.security.authentication;

import com.apocalypse.common.boot.util.HttpContextUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/7/15
 */
@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ObjectNode data = com.apocalypse.common.util.json.JsonUtil.emptyObjectNode();

        HttpContextUtil.write(response, com.apocalypse.common.util.json.JsonUtil.defaultObjectMapper()
                .createObjectNode()
                .put("code", "00000")
                .put("msg", "退出成功")
                .putPOJO("data", data));
    }
}
