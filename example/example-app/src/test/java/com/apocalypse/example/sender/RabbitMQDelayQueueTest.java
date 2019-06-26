package com.apocalypse.example.sender;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.apocalypse.example.ExampleApplication;
import com.apocalypse.example.constant.RabbitConstant;
import com.apocalypse.example.receiver.ProcessReceiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class RabbitMQDelayQueueTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //队列延迟消费，针对每个消息设置ttl过期时间
    @Test
    public void testDelayQueuePerMessageTTL() throws InterruptedException {
        ProcessReceiver.latch = new CountDownLatch(3);
        for (int i = 1; i <= 3; i++) {
            long expiration = i * 1000;
            Object msg = StrUtil.format("send time【{}】;Message From delay_queue_per_message_ttl with expiration【{}】",
                    DateUtil.date().toString(DatePattern.NORM_DATETIME_MS_PATTERN), expiration);
            rabbitTemplate.convertAndSend(RabbitConstant.DELAY_QUEUE_PER_MESSAGE_TTL_NAME, msg, message -> {
                        message.getMessageProperties().setExpiration(String.valueOf(expiration));
                        return message;
                    });
        }
        ProcessReceiver.latch.await();
    }

    //队列延迟消费，针对整个队列设置ttl过期时间
    @Test
    public void testDelayQueuePerQueueTTL() throws InterruptedException {
        ProcessReceiver.latch = new CountDownLatch(3);
        for (int i = 1; i <= 3; i++) {
            Object msg = StrUtil.format("send time【{}】;Message From delay_queue_per_queue_ttl with expiration【{}】",
                    DateUtil.date().toString(DatePattern.NORM_DATETIME_MS_PATTERN), RabbitConstant.QUEUE_EXPIRATION);
            rabbitTemplate.convertAndSend(RabbitConstant.DELAY_QUEUE_PER_QUEUE_TTL_NAME, msg);
        }
        ProcessReceiver.latch.await();
    }

    //队列延迟重试
    @Test
    public void testFailMessage() throws InterruptedException {
        ProcessReceiver.latch = new CountDownLatch(6);
        for (int i = 1; i <= 3; i++) {
            rabbitTemplate.convertAndSend(RabbitConstant.DELAY_PROCESS_QUEUE_NAME, ProcessReceiver.FAIL_MESSAGE);
        }
        ProcessReceiver.latch.await();
    }
}
