package com.apocalypse.authentication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/9/20
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 为 spring-security 认证相关的自定义页面注册资源处理器，不然会 404
        registry.addResourceHandler("/my-login.html").addResourceLocations("classpath:/public/");
        registry.addResourceHandler("/login-fail.html").addResourceLocations("classpath:/public/");
        registry.addResourceHandler("/logout.html").addResourceLocations("classpath:/public/");

        // 为静态资源注册资源处理器，不然会 404
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/layui/**").addResourceLocations("classpath:/static/layui/");
    }
}
