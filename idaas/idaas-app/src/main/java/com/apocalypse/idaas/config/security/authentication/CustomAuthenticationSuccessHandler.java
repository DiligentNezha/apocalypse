package com.apocalypse.idaas.config.security.authentication;

import com.apocalypse.common.boot.util.HttpContextUtil;
import com.apocalypse.idaas.config.security.userdetails.CustomUserDetails;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/6/9
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();

        CustomUserDetails userDetails;
        ObjectNode data = com.apocalypse.common.util.json.JsonUtil.emptyObjectNode();

        if (principal instanceof CustomUserDetails) {
            userDetails = (CustomUserDetails) principal;

            data.put("loginName", userDetails.getUsername());
        }

        HttpContextUtil.write(response, com.apocalypse.common.util.json.JsonUtil.defaultObjectMapper()
                .createObjectNode()
                .put("code", "00000")
                .put("msg", "认证成功")
                .putPOJO("data", data));
    }
}
