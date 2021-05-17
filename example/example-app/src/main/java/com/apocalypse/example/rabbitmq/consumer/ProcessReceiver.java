package com.apocalypse.example.rabbitmq.consumer;

import com.apocalypse.example.constant.RabbitConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/10
 */
@Slf4j
@Component
public class ProcessReceiver {
    public static CountDownLatch latch;

    public static final String FAIL_MESSAGE = "This message will fail";

    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.DELAY_PROCESS_QUEUE_NAME)
    public void process(String msg, Message message, Channel channel) throws Exception {
        try {
            log.info("Received <" + msg + ">");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            if (Objects.equals(msg, FAIL_MESSAGE)) {
                throw new Exception("Some exception happened");
            }
        } catch (Exception e) {
            // 如果发生了异常，则将该消息重定向到缓冲队列，会在一定延迟之后自动重做
            channel.basicPublish(RabbitConstant.PER_QUEUE_TTL_EXCHANGE_NAME, RabbitConstant.DELAY_QUEUE_PER_QUEUE_TTL_NAME,
                    null, "The failed message will auto retry after a certain delay".getBytes());
        }

        if (latch != null) {
            latch.countDown();
        }
    }

}
