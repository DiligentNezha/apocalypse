package com.apocalypse.idaas.config.security.oauth2.resource;

import com.apocalypse.idaas.config.security.ResourceIds;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources.resourceId(ResourceIds.IDAAS);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.info("ResourceServerConfig 配置");
        http.requestMatchers()
                // 资源服务器将 /open/** 端点作为资源服务器的资源
                .antMatchers("/open/**")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}
