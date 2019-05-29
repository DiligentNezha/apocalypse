package com.apocalypse.example.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="jingkaihui@adpanshi.com">jingkaihui</a>
 * @Description RabbitMQ 配置
 * @date 2019/5/27
 */
@Configuration
public class RabbitConfig {

    /**
     * 一推成功计费队列
     * @return
     */
    @Bean
    public Queue hello() {
        return new Queue("hello");
    }
}
