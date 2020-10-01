package com.apocalypse.example.controller;

import cn.hutool.core.util.RandomUtil;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.example.sender.HelloSender;
import com.apocalypse.example.sender.ProducerConfirmSender;
import com.apocalypse.example.sender.TransactionalSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/28
 */
@Slf4j
@RestController
@RequestMapping("/rabbit")
@Api(value = "RabbitMQ案例", tags = {"RabbitMQ案例"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class RabbitController {

    @Autowired
    private HelloSender helloSender;

    @Autowired
    private TransactionalSender transactionalSender;

    @Autowired
    private ProducerConfirmSender producerConfirmSender;

    @GetMapping("/oneToOne")
    @ApiOperation(value = "一对一发送", notes = "消息发送到默认的 Direct 类型的交换器，然后自动转发到 Routing Key 和 Queue Name 一致的队列")
    public Rest<BaseResponse> oneToOne() {
        helloSender.oneToOne(1);
        return Rest.success();
    }

    @GetMapping("/oneToMany")
    @ApiOperation(value = "一对多发送", notes = "消息发送到默认的 Direct 类型的交换器，然后自动转发到 Routing Key 和 Queue Name 一致的队列，RabbitMQ 会将多条消息均匀的分散到多个消费者")
    public Rest<BaseResponse> oneToMany() {
        for (int i = 0; i < 10; i++) {
            helloSender.oneToMany(i);
        }
        return Rest.success();
    }

    @GetMapping("/manyToMany")
    @ApiOperation(value = "多对多发送", notes = "消息发送到默认的 Direct 类型的交换器，然后自动转发到 Routing Key 和 Queue Name 一致的队列，RabbitMQ 会将多个生产者发送来的多条消息均匀的分散到多个消费者")
    public Rest<BaseResponse> manyToMany() {
        for (int i = 0; i < 10; i++) {
            helloSender.manyToMany1(i);
            helloSender.manyToMany2(i);
        }
        return Rest.success();
    }

    @GetMapping("/order")
    @ApiOperation(value = "Fanout 类型路由", notes = "模拟下单,消息会发送到绑定在交换器 order(fanout 类型) 上的队列 stock (减库存) 和队列 point (加积分)")
    public Rest<BaseResponse> order() {
        helloSender.order(RandomUtil.randomLong(123456789, 987654321));
        return Rest.success();
    }

    @GetMapping("/log/warn")
    @ApiOperation(value = "Topic 类型路由", notes = "模拟日志发送,消息会发送到交换器 log(topic 类型)上,交换器绑定两个队列;队列1: log_warn(处理警告日志的队列,绑定的 Routing Key log.#),队列2:log_error(处理错误日志的队列, 绑定的 Routing Key log.error),发送警告消息 log.warn 只会匹配到队列 log_warn")
    public Rest<BaseResponse> warnLog() {
        helloSender.sendWarnLog();
        return Rest.success();
    }

    @GetMapping("/log/error")
    @ApiOperation(value = "Topic 类型路由", notes = "模拟日志发送,消息会发送到交换器 log(topic 类型)上,交换器绑定两个队列;队列1: log_warn(处理警告日志的队列,绑定的 Routing Key log.#),队列2:log_error(处理错误日志的队列, 绑定的 Routing Key log.error),发送错误消息 log.error 会匹配到队列 log_error 和 log_warn")
    public Rest<BaseResponse> errorLog() {
        helloSender.sendErrorLog();
        return Rest.success();
    }

    @GetMapping("/transactional")
    @ApiOperation(value = "事务", notes = "开启rabbitMQ事务，没有异常消息正常发出")
    public Rest<BaseResponse> transactional() throws IOException, TimeoutException {
        transactionalSender.sendMessageWithTransactional();
        return Rest.success();
    }

    @GetMapping("/transactional/exception")
    @ApiOperation(value = "事务异常回滚", notes = "开启rabbitMQ事务，有运行时异常，所以事务回滚后消息没有发送成功")
    public Rest<BaseResponse> transactionalContainsException() throws IOException, TimeoutException {
        transactionalSender.sendMessageWithTransactionalContainsException();
        return Rest.success();
    }

    @GetMapping("/confirm/producer")
    @ApiOperation(value = "生产者确认", notes = "消息生产者开启确认模式，消息发送后会等待 Broker 返回 ack 或 nack")
    public Rest<BaseResponse> producerConfirm() throws IOException, TimeoutException {
        producerConfirmSender.sendProducerConfirmMessage();;
        return Rest.success();
    }

    @GetMapping("/delay/message")
    @ApiOperation(value = "发送消息到延迟队列", notes = "消息设置TTL")
    public Rest<BaseResponse> sendDelayQueueWithMessageTtl() {
        helloSender.sendDelayQueueWithMessageTtl();
        return Rest.success();
    }

    @GetMapping("/delay/queue")
    @ApiOperation(value = "发送消息到延迟队列", notes = "延迟队列设置TTL")
    public Rest<BaseResponse> sendDelayQueueWithQueueTtl() {
        helloSender.sendDelayQueueWithQueueTtl();
        return Rest.success();
    }

    @GetMapping("/delay/retry")
    @ApiOperation(value = "发送失败消息到延迟队列", notes = "发送失败的消息到队列，队列消费端出错后会将消息重新发送到延迟队列（队列设置了TTL），从而达到延迟重试的效果")
    public Rest<BaseResponse> sendFailMessage() {
        helloSender.sendFailMessage();
        return Rest.success();
    }
}
