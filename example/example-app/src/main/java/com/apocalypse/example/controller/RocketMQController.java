package com.apocalypse.example.controller;

import cn.hutool.core.util.RandomUtil;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/rocket")
@Api(value = "RocketMQ", tags = {"RocketMQ"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class RocketMQController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/message/send")
    @ApiOperation(value = "发送消息", notes = "发送消息", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> send() {
//        rocketMQTemplate.convertAndSend("test-topic-1", "hello world");
//        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("hello world").setHeader("TAGS", "test").build());
//        rocketMQTemplate.convertAndSend("test-topic-1", new Message("test-topic-1", "test", RandomUtil.randomNumbers(32), "hello world".getBytes(StandardCharsets.UTF_8)));
//        Map<String, Object> headers = new HashMap<>();
//        headers.put("TAGS", "test")
        rocketMQTemplate.convertAndSend("test-topic-1:test", "hello world");
        return Rest.success();
    }

    @GetMapping("/message/transaction/send")
    @ApiOperation(value = "发送事务消息", notes = "发送事务消息", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> transactionSend() {
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction("test-transaction-topic-1:test", MessageBuilder.withPayload("hello world! transaction msg").build(), "abc");
        transactionSendResult.setTransactionId(RandomUtil.randomString(32));
        LocalTransactionState transactionState = transactionSendResult.getLocalTransactionState();
        if (transactionState.equals(LocalTransactionState.COMMIT_MESSAGE)) {
            System.out.println("消息发送成功");
        }
        return Rest.success();
    }
}
