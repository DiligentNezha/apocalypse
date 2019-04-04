package com.apocalypse.common.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class LettucePoolConfig {

    @Bean(name = "redisTemplate")
    public RedisTemplate getRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {

        RedisTemplate<String, Serializable> template = new RedisTemplate<>();

        template.setKeySerializer(new GenericToStringSerializer(Object.class));
        template.setValueSerializer(new GenericFastJsonRedisSerializer());

        template.setHashKeySerializer(new GenericToStringSerializer(Object.class));
        template.setHashValueSerializer(new GenericFastJsonRedisSerializer());

        template.setStringSerializer(new StringRedisSerializer());

        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }
}
