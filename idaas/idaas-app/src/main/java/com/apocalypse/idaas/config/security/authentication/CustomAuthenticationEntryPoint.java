package com.gkjx.saas.health.admin.config.security.authentication;

import com.gkjx.common.boot.util.HttpContextUtil;
import com.gkjx.common.core.api.BaseResponse;
import com.gkjx.common.core.api.Rest;
import com.gkjx.common.core.enums.error.AuthenticationErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/11
 */
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Rest<BaseResponse> rest = Rest.error(AuthenticationErrorCodeEnum.INSUFFICIENT);

        log.error("未登陆", authException);
        HttpContextUtil.write(response, rest);
    }
}
