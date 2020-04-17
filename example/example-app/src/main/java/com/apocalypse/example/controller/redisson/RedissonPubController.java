package com.apocalypse.example.controller.redisson;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.redisson.codec.FastJsonCodec;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/28
 */
@Slf4j
@RestController
@RequestMapping("/redisson")
@Api(value = "Redisson Pub/Sub 案例", tags = {"Redisson Pub/Sub案例"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class RedissonPubController {

    @Autowired
    private RedissonClient redissonClient;

    public static final Map<String, Client> CLIENT_MAP = new HashMap<>();

    @GetMapping("/client/all")
    @ApiOperation(value = "获取所有 RedissonClient", notes = "获取所有 Redisson 客户端")
    public Rest<Collection<Client>> all() {
        return Rest.ok(CLIENT_MAP.values());
    }

    @GetMapping("/client/shutdown")
    @ApiOperation(value = "关闭 RedissonClient", notes = "关闭 Redisson 客户端")
    public Rest<Boolean> shutdown(@RequestParam("clientId") String clientId) {
        RedissonClient redissonClient = CLIENT_MAP.get(clientId).getRedissonClient();
        if (!redissonClient.isShutdown()) {
            redissonClient.shutdown();
        }
        return Rest.ok(redissonClient.isShutdown());
    }

    @GetMapping("/client/create")
    @ApiOperation(value = "创建 RedissonClinet", notes = "创建 Redisscon 客户端")
    public Rest<Client> create() {
        Client client = createRedissonClient();
        return Rest.ok(client);
    }

    @GetMapping("/pub")
    @ApiOperation(value = "消息发布", notes = "发布消息到指定 topic")
    public Rest<Client> push(@RequestParam("clientId") String clientId,
                             @RequestParam("topicName") String topicName,
                             @RequestParam("msg") Object msg,
                             @RequestParam("msgType") String messageType) {
        Client client = CLIENT_MAP.get(clientId);
        RTopic topic = client.getRedissonClient().getTopic(topicName);
        switch (messageType) {
            case "String":
                msg = msg.toString();break;
            case "Integer":
                msg = Integer.parseInt(msg.toString());break;
            case "Boolean":
                msg = Boolean.parseBoolean(msg.toString());break;
        }
        topic.publish(msg);
        return Rest.ok(client);
    }

    @PostMapping("/sub")
    @ApiOperation(value = "消息订阅", notes = "订阅指定 topic 消息", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<Client> push(@RequestBody SubscribeRequest subscribeRequest) {
        String clientId = subscribeRequest.getClientId();

        Client client = CLIENT_MAP.get(clientId);

        List<Subscribe> subscribes = subscribeRequest.getSubscribes();

        RedissonClient redissonClient = client.getRedissonClient();
        subscribes.stream().forEach(subscribe -> {
            String topicName = subscribe.getTopicName();
            List<String> subscribeMessageTypes = subscribe.getSubscribeMessageTypes();
            RTopic topic = redissonClient.getTopic(topicName);

            client.getChannelNames().add(topicName);

            subscribeMessageTypes.stream().forEach(messageType -> {
                MessageListener listener = buildMessageListener(clientId, messageType);
                topic.addListener(convertClass(messageType), listener);
            });
        });

        client.setSubscribes(subscribes);
        return Rest.ok(client);
    }

    Class<?> convertClass(String className) {
        switch (className) {
            case "String":
                return String.class;
            case "Integer":
                return Integer.class;
            case "Boolean":
                return Boolean.class;
        }
        return null;
    }

    public MessageListener buildMessageListener(String clientId, String messageType) {
        MessageListener messageListener;
        switch (messageType) {
            case "String": {
                messageListener = (channel, msg) -> System.out.println(StrUtil.format("[{}]收到 Topic[{}]的消息为[{}]，类型为[{}]", clientId, channel, msg, String.class));
                break;
            }
            case "Integer": {
                messageListener = (channel, msg) -> System.out.println(StrUtil.format("[{}]收到 Topic[{}]的消息为[{}]，类型为[{}]", clientId, channel, msg, Integer.class));
                break;
            }
            case "Boolean": {
                messageListener = (channel, msg) -> System.out.println(StrUtil.format("[{}]收到 Topic[{}]的消息为[{}]，类型为[{}]", clientId, channel, msg, Boolean.class));
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + messageType);
        }

        return messageListener;
    }

    public Client createRedissonClient() {
        Config config = new Config();
        config.setCodec(new FastJsonCodec());
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redissonClient = Redisson.create(config);
        String clientId = IdUtil.simpleUUID();
        Client client = new Client()
                .setClientId(clientId)
                .setRedissonClient(redissonClient);

        CLIENT_MAP.put(clientId, client);
        return client;
    }

    @Data
    @Accessors(chain = true)
    static class Client {
        private String clientId;

        private Boolean isShutdown;

        private List<String> channelNames = new ArrayList<>();

        private List<Subscribe> subscribes = new ArrayList<>();

        @JSONField(serialize = false)
        private RedissonClient redissonClient;

        public Boolean getShutdown() {
            return redissonClient.isShutdown();
        }
    }

    @Data
    @Accessors(chain = true)
    static class Subscribe {
        private String topicName;

        private List<String> subscribeMessageTypes;
    }

    @Data
    @Accessors(chain = true)
    static class SubscribeRequest {

        private String clientId;

        List<Subscribe> subscribes;
    }
}
