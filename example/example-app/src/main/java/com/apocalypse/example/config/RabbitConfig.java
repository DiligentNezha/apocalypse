package com.apocalypse.example.config;

import com.apocalypse.example.constant.RabbitConstant;
import com.apocalypse.example.receiver.ProcessReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.apocalypse.example.constant.RabbitConstant.*;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description RabbitMQ 配置
 * @date 2019/5/27
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue oneToOne() {
        return new Queue(RabbitConstant.QUEUE_ONE_TO_ONE);
    }

    @Bean
    public Queue oneToMany() {
        return new Queue(RabbitConstant.QUEUE_ONE_TO_MANY);
    }

    @Bean
    public Queue manyToMany() {
        return new Queue(RabbitConstant.QUEUE_MANY_TO_MANY);
    }

    @Bean
    public Queue stock() {
        return new Queue(RabbitConstant.QUEUE_STOCK);
    }

    @Bean
    public Queue point() {
        return new Queue(RabbitConstant.QUEUE_POINT);
    }

    @Bean
    FanoutExchange orderFanoutExchange() {
        return new FanoutExchange(RabbitConstant.FANOUT_EXCHANGE_ORDER);
    }

    @Bean
    Binding bindingStockToOrderFanoutExchange(Queue stock, FanoutExchange orderExchange) {
        return BindingBuilder.bind(stock).to(orderExchange);
    }

    @Bean
    Binding bindingPointToOrderFanoutExchange(Queue point, FanoutExchange orderExchange) {
        return BindingBuilder.bind(point).to(orderExchange);
    }

    @Bean
    public Queue logWarn() {
        return new Queue(QUEUE_LOG_WARN);
    }

    @Bean
    public Queue logError() {
        return new Queue(QUEUE_LOG_ERROR);
    }

    @Bean
    TopicExchange logTopicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_LOG);
    }

    @Bean
    Binding bindingLogWarnToLogTopicExchange(Queue logWarn, TopicExchange logExchange) {
        return BindingBuilder.bind(logWarn).to(logExchange).with(ROUTING_KEY_LOG_WARN);
    }

    @Bean
    Binding bindingLogErrorToLogTopicExchange(Queue logError, TopicExchange logExchange) {
        return BindingBuilder.bind(logError).to(logExchange).with(ROUTING_KEY_LOG_ERROR);
    }

    /**
     * 创建DLX exchange
     *
     * @return
     */
    @Bean
    DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE_NAME);
    }

    /**
     * 创建per_queue_ttl_exchange
     *
     * @return
     */
    @Bean
    DirectExchange perQueueTTLExchange() {
        return new DirectExchange(PER_QUEUE_TTL_EXCHANGE_NAME);
    }

    /**
     * 创建delay_queue_per_message_ttl队列
     *
     * @return
     */
    @Bean
    Queue delayQueuePerMessageTTL() {
        return QueueBuilder.durable(DELAY_QUEUE_PER_MESSAGE_TTL_NAME)
                // DLX，dead letter发送到的exchange
                .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE_NAME)
                // dead letter携带的routing key
                .withArgument("x-dead-letter-routing-key", DELAY_PROCESS_QUEUE_NAME)
                .build();
    }

    /**
     * 创建delay_queue_per_queue_ttl队列
     *
     * @return
     */
    @Bean
    Queue delayQueuePerQueueTTL() {
        return QueueBuilder.durable(DELAY_QUEUE_PER_QUEUE_TTL_NAME)
                // DLX
                .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE_NAME)
                // dead letter携带的routing key
                .withArgument("x-dead-letter-routing-key", DELAY_PROCESS_QUEUE_NAME)
                // 设置队列的过期时间
                .withArgument("x-message-ttl", QUEUE_EXPIRATION)
                .build();
    }

    /**
     * 创建delay_process_queue队列，也就是实际消费队列
     *
     * @return
     */
    @Bean
    Queue delayProcessQueue() {
        return QueueBuilder.durable(DELAY_PROCESS_QUEUE_NAME)
                .build();
    }

    /**
     * 将DLX绑定到实际消费队列
     *
     * @param delayProcessQueue
     * @param delayExchange
     * @return
     */
    @Bean
    Binding dlxBinding(Queue delayProcessQueue, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayProcessQueue)
                .to(delayExchange)
                .with(DELAY_PROCESS_QUEUE_NAME);
    }

    /**
     * 将per_queue_ttl_exchange绑定到delay_queue_per_queue_ttl队列（统一失效时间，用于队列延迟重试）
     *
     * @param delayQueuePerQueueTTL
     * @param perQueueTTLExchange
     * @return
     */
    @Bean
    Binding queueTTLBinding(Queue delayQueuePerQueueTTL, DirectExchange perQueueTTLExchange) {
        return BindingBuilder.bind(delayQueuePerQueueTTL)
                .to(perQueueTTLExchange)
                .with(DELAY_QUEUE_PER_QUEUE_TTL_NAME);
    }

    /**
     * 定义delay_process_queue队列的Listener Container
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    SimpleMessageListenerContainer processContainer(ConnectionFactory connectionFactory, ProcessReceiver processReceiver) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 监听delay_process_queue
        container.setQueueNames(DELAY_PROCESS_QUEUE_NAME);
        container.setMessageListener(new MessageListenerAdapter(processReceiver));
        return container;
    }
}
