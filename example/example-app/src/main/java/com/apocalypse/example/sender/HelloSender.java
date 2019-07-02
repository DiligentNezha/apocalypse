package com.apocalypse.example.sender;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.apocalypse.example.constant.RabbitConstant;
import com.apocalypse.example.receiver.ProcessReceiver;
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

    /**
     * 发送到延迟队列,对消息设置TTL
     */
    public void sendDelayQueueWithMessageTtl() {
        int expiration = 10 * 1000;
        Object msg = StrUtil.format("send time【{}】;Message From delay_queue_per_message_ttl with expiration【{}】",
                DateUtil.date().toString(DatePattern.NORM_DATETIME_MS_PATTERN), expiration);
        rabbitTemplate.convertAndSend(RabbitConstant.DELAY_QUEUE_PER_MESSAGE_TTL_NAME, msg, message -> {
            message.getMessageProperties().setExpiration(String.valueOf(expiration));
            return message;
        });
        log.info("HelloSender send delay queue with message ttl【{}】", msg);
    }

    /**
     * 发送消息到延迟队列，对队列设置TTL
     */
    public void sendDelayQueueWithQueueTtl() {
        for (int i = 1; i <= 3; i++) {
            Object msg = StrUtil.format("send time【{}】;Message From delay_queue_per_queue_ttl with expiration【{}】",
                    DateUtil.date().toString(DatePattern.NORM_DATETIME_MS_PATTERN), RabbitConstant.QUEUE_EXPIRATION);
            rabbitTemplate.convertAndSend(RabbitConstant.DELAY_QUEUE_PER_QUEUE_TTL_NAME, msg);
            log.info("HelloSender send delay queue with queue ttl【{}】", msg);
        }
    }

    /**
     * 发送失败的消息到队列，队列消费端出错后会将消息重新发送到延迟队列（队列设置了TTL），从而达到延迟重试的效果
     */
    public void sendFailMessage() {
        for (int i = 1; i <= 3; i++) {
            String msg = ProcessReceiver.FAIL_MESSAGE;
            rabbitTemplate.convertAndSend(RabbitConstant.DELAY_PROCESS_QUEUE_NAME, msg);
            log.info("HelloSender send fail message【{}】", msg);
        }
    }
}
