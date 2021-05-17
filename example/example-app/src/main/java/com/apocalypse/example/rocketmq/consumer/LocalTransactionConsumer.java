package com.apocalypse.example.rocketmq.consumer;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2021/5/9
 */
@Slf4j
@Service
@RocketMQTransactionListener
public class LocalTransactionConsumer implements RocketMQLocalTransactionListener {

    private List<String> transactionIds = new ArrayList<>();

    private Map<String, Integer> stringIntegerMap = new HashMap<>();

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String plainMsg = StrUtil.str(msg.getPayload(), StandardCharsets.UTF_8);
        log.info("received message: {}, {}", plainMsg, arg);
        transactionIds.add(msg.getHeaders().getId().toString());
        return RocketMQLocalTransactionState.ROLLBACK;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String msgId = msg.getHeaders().getId().toString();
        RocketMQLocalTransactionState transactionState = RocketMQLocalTransactionState.ROLLBACK;
        if (!stringIntegerMap.containsKey(msgId)) {
            log.info("state check:{}，{}", msgId, 1);
            stringIntegerMap.put(msgId, 1);
        } else {
            Integer integer = stringIntegerMap.get(msgId);
            if (integer.intValue() == 3) {
                log.info("state check success:{}，{}", msgId, 3);
                transactionState = RocketMQLocalTransactionState.COMMIT;
            } else {
                stringIntegerMap.put(msgId, integer++);
            }
        }
        return transactionState;
    }
}

