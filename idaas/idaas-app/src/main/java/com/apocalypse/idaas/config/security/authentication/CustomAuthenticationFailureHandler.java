package com.apocalypse.idaas.config.security.authentication;

import com.apocalypse.common.boot.util.HttpContextUtil;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.common.core.enums.error.AuthenticationErrorCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
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
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Rest<BaseResponse> rest = Rest.error(AuthenticationErrorCodeEnum.FAILURE);
        if (exception instanceof UsernameNotFoundException) {
            rest = Rest.error(AuthenticationErrorCodeEnum.USERNAME_NOT_FOUND);
        } else if (exception instanceof BadCredentialsException) {
            rest = Rest.error(AuthenticationErrorCodeEnum.BAD_CREDENTIALS);
        } else if (exception instanceof SessionAuthenticationException) {
            rest = Rest.error(AuthenticationErrorCodeEnum.LOGIN_ON_ANOTHER_DEVICE, "本次登录失败");
        }
        HttpContextUtil.write(response, rest);
    }
}
