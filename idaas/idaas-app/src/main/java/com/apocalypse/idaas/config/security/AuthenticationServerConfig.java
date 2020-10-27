package com.apocalypse.idaas.config.security;

import com.apocalypse.common.boot.util.HttpContextUtil;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.common.core.enums.error.AuthenticationErrorCodeEnum;
import com.apocalypse.idaas.config.security.authentication.CustomAuthenticationEntryPoint;
import com.apocalypse.idaas.config.security.authorization.CustomAccessDeniedHandler;
import com.apocalypse.idaas.constants.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description 认证相关配置
 * @date 2020/10/16
 */
@Slf4j
@EnableWebSecurity(debug = true)
public class AuthenticationServerConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationDetailsSource authenticationDetailsSource;

    @Autowired
    private FindByIndexNameSessionRepository sessionRepository;

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("AuthorizationServerConfig 配置");
        http
                .csrf()
                .disable()
                .cors()
                .configurationSource(corsConfigurationSource());
        http
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler());
                // 资源服务器不能指定，指定后将不生成页面，也就没有办法跳转了
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        http
                .formLogin()
                    .loginProcessingUrl(SecurityConstants.LOGIN_PROCESSING_URL)
                    .usernameParameter("loginName")
                    .defaultSuccessUrl("/doc.html")
//                    .successHandler(successHandler)
//                    .failureHandler(failureHandler)
                    .authenticationDetailsSource(authenticationDetailsSource)
                .permitAll();
        http
                .logout()
                .logoutUrl(SecurityConstants.AUTH_LOGOUT)
                .logoutSuccessHandler(logoutSuccessHandler)
                // 放开登录退出页，同登录页
                .permitAll();
        http
                .authorizeRequests()
                    .antMatchers(SecurityConstants.AUTH_CAPTCHA)
                    .permitAll()
                .and()
                .authorizeRequests()
                    .antMatchers("/h2-console/**")
                    .permitAll()
                .anyRequest()
                .access("@customSecurityExpressionRoot.hasPermission(request, authentication)");

        http
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer.sessionConcurrency(concurrencyControlConfigurer -> {
                        // 触发创建 ConcurrentSessionFilter
                        concurrencyControlConfigurer.maximumSessions(1);
                        // TODO session 失效在 OAuth2 环境下需要前端配合做页面跳转
//                        concurrencyControlConfigurer.expiredSessionStrategy(event -> {
//                            HttpServletResponse response = event.getResponse();
//                            SessionInformation sessionInformation = event.getSessionInformation();
//                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                            Rest<BaseResponse> rest = Rest.error(AuthenticationErrorCodeEnum.LOGIN_ON_ANOTHER_DEVICE, "本次登录失效");
//                            HttpContextUtil.write(response, rest);
//                        });
                    });

//                    httpSecuritySessionManagementConfigurer.invalidSessionStrategy((request, response) -> {
//                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                        HttpContextUtil.write(response, Rest.error(AuthenticationErrorCodeEnum.CREDENTIALS_EXPIRED));
//                    });
                });
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        // 静态资源请求放行
        String[] swaggerExportPaths = {"/doc.html", "/api-docs-ext", "/swagger-resources", "/api-docs", "/swagger-ui.html",
                "/swagger-resources/configuration/ui", "/swagger-resources/configuration/security",
                "/manifest.json", "/robots.txt", "/service-worker.js", "/v2/api-docs-ext", "/v2/api-docs",
                "/favicon.ico", "/index.html", "/webjars/css/chunk-*.css", "/ui",
                "/webjars/css/app.*.css", "/webjars/js/app.*.js", "/webjars/js/chunk-*.js",
                "/precache-manifest.*.js"};
        web.ignoring()
                .antMatchers(swaggerExportPaths)
                .antMatchers("/actuator/health")
                .antMatchers("/actuator/prometheus");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//        builder.authenticationProvider(authenticationProvider());
        builder.inMemoryAuthentication().withUser("root").password(passwordEncoder.encode("root")).roles("USER");
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
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
//        corsConfiguration.setExposedHeaders(Arrays.asList("X-Auth-Token"));
        corsConfiguration.setAllowCredentials(true);
        //配置允许跨域访问的url
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
