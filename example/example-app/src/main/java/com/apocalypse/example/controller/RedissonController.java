package com.apocalypse.example.controller;

import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.redisson.codec.FastJsonCodec;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/28
 */
@Slf4j
@RestController
@RequestMapping("/redisson")
public class RedissonController {

    /**
     * 单节点模式
     * @return
     */
    @GetMapping("/single")
    public Rest<Config> single() {
        // 默认连接地址 127.0.0.1:6379
        // RedissonClient redisson = Redisson.create();

        Config config = new Config();
        config.setCodec(new FastJsonCodec());
        config.useSingleServer().setAddress("redis://127.0.0.1:6301");
        RedissonClient redisson = Redisson.create(config);
        redisson.shutdown();
        return Rest.ok(config);
    }

    /**
     * 主从模式
     * @return
     */
    @GetMapping("/masterSlave")
    public Rest<Config> masterSlave() {
        Config config = new Config();
        config.setCodec(new FastJsonCodec());
        config.useMasterSlaveServers()
                //可以用"rediss://"来启用SSL连接
                .setMasterAddress("redis://127.0.0.1:6301")
                .addSlaveAddress("redis://127.0.0.1:6302", "redis://127.0.0.1:6303");
        RedissonClient redisson = Redisson.create(config);
        redisson.shutdown();
        return Rest.ok(config);
    }

    /**
     * 哨兵模式
     * @return
     */
    @GetMapping("/sentinel")
    public Rest<Config> sentinel() {
        Config config = new Config();
        config.setCodec(new FastJsonCodec());
        config.useSentinelServers()
                .setMasterName("master")
                //可以用"rediss://"来启用SSL连接
                .addSentinelAddress("redis://127.0.0.1:7301", "redis://127.0.0.1:7302", "redis://127.0.0.1:7303");

        RedissonClient redisson = Redisson.create(config);
        redisson.shutdown();
        return Rest.ok(config);
    }

    /**
     * 集群模式
     * @return
     */
    @GetMapping("/cluster")
    public Rest<Config> cluster() {
        Config config = new Config();
        config.setCodec(new FastJsonCodec());
        config.useClusterServers()

                // 集群状态扫描间隔时间，单位是毫秒
                .setScanInterval(2000)
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress("redis://127.0.0.1:6301", "redis://127.0.0.1:6302", "redis://127.0.0.1:6303");

        RedissonClient redisson = Redisson.create(config);
        redisson.shutdown();
        return Rest.ok(config);
    }
}
