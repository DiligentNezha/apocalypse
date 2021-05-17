package com.apocalypse.cms.oauth2.client;

import com.apocalypse.cms.oauth2.client.registrations.dingtalk.DingTalkAuth2AccessTokenResponseClient;
import com.apocalypse.cms.oauth2.client.registrations.dingtalk.DingTalkOAuth2User;
import com.apocalypse.cms.oauth2.client.registrations.idaas.IdaasOAuth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.UriBuilder;
import tk.mybatis.mapper.weekend.Fn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;


/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/20
 */
@EnableWebSecurity(debug = true)
public class OAuth2ClientConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

//    @Autowired
//    private FindByIndexNameSessionRepository sessionRepository;
//
//    @Bean
//    public SessionRegistry sessionRegistry() {
//        return new SpringSessionBackedSessionRegistry(sessionRepository);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors()
                .configurationSource(corsConfigurationSource());

        http.oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/me")
//                .loginPage("/login")
                .authorizationEndpoint(authorization -> {
                    authorization
//                            .authorizationRequestRepository(new HttpSessionOAuth2AuthorizationRequestRepository())
//                            .baseUri("/idaas/oauth2/authroization")
                            .authorizationRequestResolver(auth2AuthorizationRequestResolver());

                })
                .redirectionEndpoint(redirection -> {
                    redirection
                            .baseUri("/call/**");
                })
                .userInfoEndpoint(userInfo -> {
                    userInfo.userService(oAuth2UserService())
                            .customUserType(DingTalkOAuth2User.class, OAuth2RegistrationIds.DING_TALK)
                            .customUserType(IdaasOAuth2User.class, OAuth2RegistrationIds.IDAAS);
                })
                .tokenEndpoint(token -> {
                    token.accessTokenResponseClient(accessTokenResponseClient());
                }))
                .authorizeRequests()
                .antMatchers("/ball/**")
                    .permitAll()
                .antMatchers("/login")
                    .permitAll()
                .antMatchers("/public/**")
                    .permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public OAuth2AuthorizationRequestResolver auth2AuthorizationRequestResolver() {
        DefaultOAuth2AuthorizationRequestResolver defaultOAuth2AuthorizationRequestResolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, DEFAULT_AUTHORIZATION_REQUEST_BASE_URI);
        defaultOAuth2AuthorizationRequestResolver.setAuthorizationRequestCustomizer(builder -> {
            builder.authorizationRequestUri(new Fn<UriBuilder, URI>() {
                @Override
                public URI apply(UriBuilder uriBuilder) {
                    URI uri = uriBuilder.build();
                    if (uri.getHost().contains("dingtalk")) {
                        String replace = uri.toString().replace("client_id", "appid");
                        try {
                            uri = new URI(replace);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                    return uri;
                }
            });
        });
        return defaultOAuth2AuthorizationRequestResolver;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 静态资源请求放行
        String[] swaggerExportPaths = {"/doc.html", "/api-docs-ext", "/swagger-resources", "/api-docs", "/swagger-ui.html",
                "/swagger-resources/configuration/ui", "/swagger-resources/configuration/security",
                "/manifest.json", "/robots.txt", "/service-worker.js", "/v2/api-docs-ext", "/v2/api-docs",
                "/favicon.ico", "/index.html", "/webjars/css/chunk-*.css",
                "/webjars/css/app.*.css", "/webjars/js/app.*.js", "/webjars/js/chunk-*.js",
                "/precache-manifest.*.js", "/ball/**", "/sso/**", "/public/**"};
        web.ignoring()
                .antMatchers(swaggerExportPaths)
                .antMatchers("/actuator/prometheus");
    }

    private OAuth2AccessTokenResponseClient accessTokenResponseClient() {
        Map<String, OAuth2AccessTokenResponseClient> customClients = new HashMap<>();
        customClients.put(OAuth2RegistrationIds.DING_TALK, new DingTalkAuth2AccessTokenResponseClient());
        CompositeOAuth2AccessTokenResponseClient accessTokenResponseClient = new CompositeOAuth2AccessTokenResponseClient(customClients);
        return accessTokenResponseClient;
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        CompositeOAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new CompositeOAuth2UserService<>();
        return oAuth2UserService;
    }

    private CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
//        corsConfiguration.addAllowedOrigin("*.apocalypse.com");
        corsConfiguration.addAllowedOriginPattern("*.apocalypse.com");
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
