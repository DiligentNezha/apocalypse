package com.apocalypse.example.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2021/5/9
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "test-topic-1", selectorExpression = "test", consumerGroup = "my-consumer-test-topic-1")
public class StringConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("received message: {}", message);
    }
}
