package com.apocalypse.example.controller.ps;

import com.apocalypse.common.redisson.codec.FastJsonCodec;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.StatusListener;
import org.redisson.config.Config;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/4/15
 */
public class RedisPub {
    public static void main(String[] args) {
        Config config = new Config();
        config.setCodec(new FastJsonCodec());
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);
        RTopic topic = redisson.getTopic("order");
        topic.addListener(new StatusListener() {
            @Override
            public void onSubscribe(String channel) {
                System.out.println("sub:" + channel);
            }

            @Override
            public void onUnsubscribe(String channel) {
                System.out.println("unSub:" + channel);
            }
        });
    }
}
