package com.apocalypse.example.sender;

import cn.hutool.core.util.IdUtil;
import com.apocalypse.example.constant.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description rabbitMq发送者
 * @date 2019/5/27
 */
@Slf4j
@Component
public class HelloSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 一对一发送，发送者的路由key和队列名一致
     * @param i
     */
    public void oneToOne(Integer i) {
        rabbitTemplate.convertAndSend(RabbitConstant.QUEUE_ONE_TO_ONE, i, message -> {
            message.getMessageProperties().setMessageId(IdUtil.simpleUUID());
            return message;
        });
        log.info("HelloSender send one to one message【{}】", i);
    }

    /**
     * 一对多发送，发送者的路由key和队列名一致
     * @param i
     */
    public void oneToMany(Integer i) {
        rabbitTemplate.convertAndSend(RabbitConstant.QUEUE_ONE_TO_MANY, i, message -> {
            message.getMessageProperties().setMessageId(IdUtil.simpleUUID());
            return message;
        });
        log.info("HelloSender send one to many message【{}】", i);
    }

    /**
     * 多对多发送，发送者的路由key和队列名一致
     * @param i
     */
    public void manyToMany1(Integer i) {
        rabbitTemplate.convertAndSend(RabbitConstant.QUEUE_MANY_TO_MANY, i, message -> {
            message.getMessageProperties().setMessageId(IdUtil.simpleUUID());
            return message;
        });
        log.info("HelloSender manyToMany1 send many to many message【{}】", i);
    }

    /**
     * 多对多发送，发送者的路由key和队列名一致
     * @param i
     */
    public void manyToMany2(Integer i) {
        rabbitTemplate.convertAndSend(RabbitConstant.QUEUE_MANY_TO_MANY, i, message -> {
            message.getMessageProperties().setMessageId(IdUtil.simpleUUID());
            return message;
        });
        log.info("HelloSender manyToMany2 send many to many message【{}】", i);
    }

    /**
     * 下单
     */
    public void order(Long userId) {
        rabbitTemplate.convertAndSend(RabbitConstant.FANOUT_EXCHANGE_ORDER, null, userId);
        log.info("HelloSender send order message userId【{}】", userId);
    }

    /**
     * 发送错误日志
     */
    public void sendErrorLog() {
        String msg = "this is error log";
        // 发送Error Log，com.apocalypse.example.config.RabbitConfig.logWarn 和
        // com.apocalypse.example.config.RabbitConfig.logError 都可以收到消息
        rabbitTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGE_LOG, "log.error", msg);
        log.info("HelloSender send error log【{}】", msg);
    }

    /**
     * 发送错误日志
     */
    public void sendWarnLog() {
        String msg = "this is warn log";
        // 发送Warn Log，只有com.apocalypse.example.config.RabbitConfig.logWarn 可以收到消息
        rabbitTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGE_LOG, "log.warn", msg);
        log.info("HelloSender send warn log【{}】", msg);
    }
}
