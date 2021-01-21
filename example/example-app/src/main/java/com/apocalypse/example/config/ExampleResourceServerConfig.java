package com.apocalypse.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Map;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Configuration
@EnableResourceServer
@EnableWebSecurity(debug = true)
public class ExampleResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources.resourceId("Example");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.info("Example ResourceServerConfig 配置");
        http.requestMatchers()
                // 资源服务器将 /open/** 端点作为资源服务器的资源
                .antMatchers("/open/**")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();

    }

    @Autowired
    private ResourceServerProperties sso;

    @Autowired
    private UserInfoRestTemplateFactory userInfoRestTemplateFactory;

    @Bean
    public UserInfoTokenServices userInfoTokenServices() {
        UserInfoTokenServices services = new UserInfoTokenServices(
                this.sso.getUserInfoUri(), this.sso.getClientId());
        services.setRestTemplate(userInfoRestTemplateFactory.getUserInfoRestTemplate());
        services.setTokenType(this.sso.getTokenType());
        services.setPrincipalExtractor(new PrincipalExtractor() {
            @Override
            public Object extractPrincipal(Map<String, Object> map) {
                // 实际类型需转换成 CustomUserDetails 对象
                return ((Map) map.get("data")).get("principal");
            }
        });
        return services;
    }
}
