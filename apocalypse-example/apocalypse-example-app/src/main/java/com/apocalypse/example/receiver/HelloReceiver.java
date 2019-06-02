package com.apocalypse.example.receiver;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author <a href="jingkaihui@adpanshi.com">jingkaihui</a>
 * @Description rabbitMq接受者
 * @date 2019/5/27
 */
@Slf4j
@Component
@RabbitListener(queues = "hello")
public class HelloReceiver {

    @RabbitHandler
    public void process(Integer i, Message message, Channel channel) {
        try {
            log.info("HelloReceiver received message【{}】", i);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //报错容错处理
        }
    }

}
