package com.apocalypse.example.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="jingkaihui@adpanshi.com">jingkaihui</a>
 * @Description rabbitMq发送者
 * @date 2019/5/27
 */
@Slf4j
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(Integer i) {
        amqpTemplate.convertAndSend("hello", i);
        log.info("HelloSender send message【{}】", i);
    }
}
