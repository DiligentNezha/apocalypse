package com.apocalypse.idaas.config;

import com.apocalypse.common.boot.util.HttpContextUtil;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.common.core.enums.error.AuthenticationErrorCodeEnum;
import com.apocalypse.idaas.constants.SecurityConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description 认证服务器配置
 * @date 2020/10/16
 */
@Configuration
@EnableWebSecurity
public class AuthorizationServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .cors()
                .configurationSource(corsConfigurationSource());
        http
                .headers()
                    .frameOptions()
                    .disable();
        http
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        http
                .formLogin()
                .permitAll();
        http
                .logout()
                .logoutUrl(SecurityConstants.AUTH_LOGOUT)
                .logoutSuccessHandler(logoutSuccessHandler)
                // 放开登录退出页，同登录页
                .permitAll();
        http
                .authorizeRequests()
                .anyRequest()
                .access("@customSecurityExpressionRoot.hasPermission(request, authentication)");

        http
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer.sessionConcurrency(concurrencyControlConfigurer -> {
                        // 触发创建 ConcurrentSessionFilter
                        concurrencyControlConfigurer.maximumSessions(1);
                        concurrencyControlConfigurer.expiredSessionStrategy(event -> {
                            HttpServletResponse response = event.getResponse();
                            SessionInformation sessionInformation = event.getSessionInformation();
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            Rest<BaseResponse> rest = Rest.error(AuthenticationErrorCodeEnum.LOGIN_ON_ANOTHER_DEVICE, "本次登录失效");
                            HttpContextUtil.write(response, rest);
                        });
                    });

                    httpSecuritySessionManagementConfigurer.invalidSessionStrategy((request, response) -> {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        HttpContextUtil.write(response, Rest.error(AuthenticationErrorCodeEnum.CREDENTIALS_EXPIRED));
                    });
                });
        super.configure(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    private CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedOrigin("*");
        //header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedHeader("*");
        //允许的请求方法，PSOT、GET等
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setExposedHeaders(Arrays.asList("X-Auth-Token"));
        corsConfiguration.setAllowCredentials(true);
        //配置允许跨域访问的url
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
