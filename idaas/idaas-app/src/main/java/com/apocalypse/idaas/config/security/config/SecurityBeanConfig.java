package com.apocalypse.idaas.config.security.config;

import com.apocalypse.idaas.config.security.authentication.CustomWebAuthenticationDetails;
import com.apocalypse.idaas.config.security.authentication.CustomWebAuthenticationDetailsSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.*;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/7/23
 */
@Configuration
public class SecurityBeanConfig {

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationDetailsSource<HttpServletRequest, CustomWebAuthenticationDetails> authenticationDetailsSource() {
        return new CustomWebAuthenticationDetailsSource();
    }

    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

//    @Bean
//    public SessionRepositoryCustomizer sessionRepositoryCustomizer() {
//        return new SessionRepositoryCustomizer<RedisIndexedSessionRepository>() {
//            @Override
//            public void customize(RedisIndexedSessionRepository redisIndexedSessionRepository) {
//                RedisOperations<Object, Object> redisOperations = redisIndexedSessionRepository.getSessionRedisOperations();
//                RedisTemplate redisTemplate = (RedisTemplate) redisOperations;
//                RedisSerializer<String> string = RedisSerializer.string();
//                redisTemplate.setKeySerializer(string);
//                GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
//                redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//                redisTemplate.setHashKeySerializer(string);
//                redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//            }
//        };
//    }
}
