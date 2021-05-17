package com.apocalypse.example.rabbitmq.consumer;

import com.apocalypse.example.constant.RabbitConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/1
 */
@Slf4j
@Component
public class TransactionalReceiver {

    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.QUEUE_TRANSACTIONAL)
    public void processSendMessageWithTransactional(String msg, Message message, Channel channel) {
        try {
            log.info("TransactionalReceiver received message with transactional! message【{}】", msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //报错容错处理
        }
    }
}
