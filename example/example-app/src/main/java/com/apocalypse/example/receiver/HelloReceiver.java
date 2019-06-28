package com.apocalypse.example.receiver;

import com.apocalypse.example.constant.RabbitConstant;
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
public class HelloReceiver {

    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.QUEUE_ONE_TO_ONE)
    public void processOneToOne(Integer i, Message message, Channel channel) {
        try {
            log.info("HelloReceiver received one to one message【{}】; messageId【{}】", i,
                    message.getMessageProperties().getMessageId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //报错容错处理
        }
    }

    /**
     * RabbitMQ 会把消息均匀的分发给 processOneToMany1, processOneToMany2, processOneToMany3 处理
     * 详见 com.gndc.demo.sender.HelloSenderTest#oneToMany()
     * @param i
     * @param message
     * @param channel
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.QUEUE_ONE_TO_MANY)
    public void processOneToMany1(Integer i, Message message, Channel channel) {
        try {
            log.info("HelloReceiver1 received one to many message【{}】; messageId【{}】", i,
                    message.getMessageProperties().getMessageId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //报错容错处理
        }
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.QUEUE_ONE_TO_MANY)
    public void processOneToMany2(Integer i, Message message, Channel channel) {
        try {
            log.info("HelloReceiver2 received one to many message【{}】; messageId【{}】", i,
                    message.getMessageProperties().getMessageId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //报错容错处理
        }
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.QUEUE_ONE_TO_MANY)
    public void processOneToMany3(Integer i, Message message, Channel channel) {
        try {
            log.info("HelloReceiver3 received one to many message【{}】; messageId【{}】", i,
                    message.getMessageProperties().getMessageId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //报错容错处理
        }
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.QUEUE_MANY_TO_MANY)
    public void processManyToMany1(Integer i, Message message, Channel channel) {
        try {
            log.info("HelloReceiver1 received many to many message【{}】; messageId【{}】", i,
                    message.getMessageProperties().getMessageId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //报错容错处理
        }
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.QUEUE_MANY_TO_MANY)
    public void processManyToMany2(Integer i, Message message, Channel channel) {
        try {
            log.info("HelloReceiver2 received many to many message【{}】; messageId【{}】", i,
                    message.getMessageProperties().getMessageId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //报错容错处理
        }
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.QUEUE_MANY_TO_MANY)
    public void processManyToMany3(Integer i, Message message, Channel channel) {
        try {
            log.info("HelloReceiver3 received many to many message【{}】; messageId【{}】", i,
                    message.getMessageProperties().getMessageId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //报错容错处理
        }
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.QUEUE_STOCK)
    public void processStock(Long userId, Message message, Channel channel) {
        try {
            log.info("HelloReceiver received order message userId【{}】; stock decrement", userId);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //报错容错处理
        }
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.QUEUE_POINT)
    public void processPoint(Long userId, Message message, Channel channel) {
        try {
            log.info("HelloReceiver received order message userId【{}】; point increment", userId);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //报错容错处理
        }
    }
}
