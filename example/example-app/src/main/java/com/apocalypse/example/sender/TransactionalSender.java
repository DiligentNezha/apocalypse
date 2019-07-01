package com.apocalypse.example.sender;

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
 * @Description
 * @date 2019/7/1
 */
@Slf4j
@Component
public class TransactionalSender {

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    public void sendMessageWithTransactional() throws IOException, TimeoutException {
        ConnectionFactory rabbitConnectionFactory = cachingConnectionFactory.getRabbitConnectionFactory();
        Connection connection = rabbitConnectionFactory.newConnection();
        Channel channel = connection.createChannel();
        try {
            channel.txSelect();
            String msg = "这是一条开启事务发出的消息";
            channel.basicPublish("", RabbitConstant.QUEUE_TRANSACTIONAL,
                    MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            channel.txCommit();
            log.info("TransactionalSender send message with transactional! message【{}】", msg);
        } catch (Exception e) {
            log.error("出现异常,事务回滚，消息未发出", e);
            channel.txRollback();
        } finally {
            channel.close();
            connection.close();
        }
    }

    public void sendMessageWithTransactionalContainsException() throws IOException, TimeoutException {
        ConnectionFactory rabbitConnectionFactory = cachingConnectionFactory.getRabbitConnectionFactory();
        Connection connection = rabbitConnectionFactory.newConnection();
        Channel channel = connection.createChannel();
        try {
            channel.txSelect();
            String msg = "这是一条开启事务发出的消息";
            channel.basicPublish("", RabbitConstant.QUEUE_TRANSACTIONAL,
                    MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            int i = 10 / 0;
            channel.txCommit();
            log.info("TransactionalSender send message with transactional! message【{}】", msg);
        } catch (Exception e) {
            log.error("出现异常,事务回滚，消息未发出", e);
            channel.txRollback();
        } finally {
            channel.close();
            connection.close();
        }
    }
}
