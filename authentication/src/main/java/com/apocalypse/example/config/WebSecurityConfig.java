package com.apocalypse.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/9/19
 */
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static String PASSWORD = "root";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
            .formLogin()
                // 自定义登录页、登录失败页、退出登录页必须放在 public 或者 static 中（或者权限放开，总之，
                // 不能让 spring-security 认为这是一个需要认证才能访问的路径），不然会出现404
                .loginPage("/my-login.html")
                // 指定表单登录认证的处理路径
                .loginProcessingUrl("/auth/form")
                // 登录成功后跳转路径
                .defaultSuccessUrl("/home")
                .failureUrl("/login-fail.html")
                // 必须放开 my-login.html,不然会出现 localhost 将您重定向的次数过多，原因是使用的 anyRequest().authenticated(),
                // 所有请求都需要授权，首先跳转到 my-login.html 请求登录，发现需要授权，然后就跳转到 my-login.html 去请求授权，结果就
                // 出现死循环了
                // 必须放开 login-fail.html 不然登录失败后，会从定向到登录页
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/logout.html")
                // 放开登录退出页，同登录页
                .permitAll()
                .and()
            .authorizeRequests()
                .antMatchers("/layui/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/layui/**");
    }

    @Autowired
    public void authentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memoryUserDetailsManager());
    }

    /**
     * UserDetailsManager 继承自 UserDetailsService,提供了一些 User管理的内容
     * @return
     */
    @Bean
    public UserDetailsManager memoryUserDetailsManager() {
        String encodePassword = passwordEncoder().encode(PASSWORD);
        System.out.println("password encode: " + encodePassword);
        InMemoryUserDetailsManager memoryUserDetailsManager = new InMemoryUserDetailsManager(User.withUsername("root")
                .password(encodePassword).authorities("ADMIN", "USER").build());
        return memoryUserDetailsManager;
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
