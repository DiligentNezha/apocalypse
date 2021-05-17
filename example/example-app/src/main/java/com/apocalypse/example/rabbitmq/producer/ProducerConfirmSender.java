package com.apocalypse.example.rabbitmq.producer;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.apocalypse.example.constant.RabbitConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description 生产者确认模式
 * @date 2019/7/1
 */
@Slf4j
@Component
public class ProducerConfirmSender {

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    /**
     * 发送一条生产者确认消息
     * @throws IOException
     * @throws TimeoutException
     */
    public void sendProducerConfirmMessage() throws IOException, TimeoutException {
        ConnectionFactory rabbitConnectionFactory = cachingConnectionFactory.getRabbitConnectionFactory();
        Connection connection = rabbitConnectionFactory.newConnection();
        Channel channel = connection.createChannel();
        try {
            if (RandomUtil.randomBoolean()) {
                singleConfirm(channel);
            } else {
                batchConfirm(channel);
            }
        } catch (Exception e) {
            log.error("出现异常,消息未发出", e);
        } finally {
            channel.close();
            connection.close();
        }
    }

    /**
     * 消息单条确认，每发送一条，就等待服务端确认
     * @param channel
     * @throws IOException
     * @throws InterruptedException
     */
    private void singleConfirm(Channel channel) throws IOException, InterruptedException {
        String msg = "这是一条需要生产者确认的消息";
        // 设置 Channel 为 confirm 模式
        channel.confirmSelect();
        channel.basicPublish("", RabbitConstant.QUEUE_PRODUCER_CONFIRM,
                MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        // 等待 broker 返回 ack 或 nack 消息
        if (channel.waitForConfirms()) {
            log.info("ProducerConfirmSender send producer confirm message success! message【{}】", msg);
        }
    }

    /**
     * 消息批量确认，发送多条消息，等待服务端确认
     * @param channel
     * @throws IOException
     * @throws InterruptedException
     */
    private void batchConfirm(Channel channel) throws IOException, InterruptedException {
        String msg = "这是一条需要生产者确认的消息{}";
        // 设置 Channel 为 confirm 模式
        channel.confirmSelect();
        for (int i = 0; i < 100; i++) {
            channel.basicPublish("", RabbitConstant.QUEUE_PRODUCER_CONFIRM,
                MessageProperties.PERSISTENT_TEXT_PLAIN, StrUtil.format(msg, i).getBytes());
        }
        long start = System.currentTimeMillis();
        // 等待最后一条消息 ack 或 nack 才会结束
        // 该方式会造成程序阻塞，并且不能拿到 ack 消息
//        channel.waitForConfirmsOrDie();
        channel.addConfirmListener((deliveryTag, multiple) -> {
            String confirmMsg = StrUtil.format("ack:deliveryTag:{};multiple:{}", deliveryTag, multiple);
            log.info(confirmMsg);
        }, (deliveryTag, multiple) -> {
            String confirmMsg = StrUtil.format("nack:deliveryTag:{};multiple:{}", deliveryTag, multiple);
            log.info(confirmMsg);
        });
        log.info("执行waitForConfirmsOrDie耗费时间:{}ms", System.currentTimeMillis() - start);
    }
}
