package com.apocalypse.example.receiver;

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
public class ProducerConfirmReceiver {

    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.QUEUE_PRODUCER_CONFIRM)
    public void processSendMessageWithTransactional(String msg, Message message, Channel channel) {
        try {
            log.info("ProducerConfirmReceiver received producer confirmed message! message【{}】;messageId【{}】", msg,
                    message.getMessageProperties().getMessageId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //报错容错处理
        }
    }
}
