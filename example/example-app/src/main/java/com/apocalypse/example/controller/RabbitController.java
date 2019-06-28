package com.apocalypse.example.controller;

import cn.hutool.core.util.RandomUtil;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.example.sender.HelloSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/rabbit")
public class RabbitController {

    @Autowired
    private HelloSender helloSender;

    @GetMapping("/oneToOne")
    public Rest<Boolean> oneToOne() {
        helloSender.oneToOne(1);
        return Rest.ok();
    }

    @GetMapping("/oneToMany")
    public Rest<Boolean> oneToMany() {
        for (int i = 0; i < 10; i++) {
            helloSender.oneToMany(i);
        }
        return Rest.ok();
    }

    @GetMapping("/manyToMany")
    public Rest<Boolean> manyToMany() {
        for (int i = 0; i < 10; i++) {
            helloSender.manyToMany1(i);
            helloSender.manyToMany2(i);
        }
        return Rest.ok();
    }

    @GetMapping("/order")
    public Rest<Boolean> order() {
        helloSender.order(RandomUtil.randomLong(123456789, 987654321));
        return Rest.ok();
    }

    @GetMapping("/log/warn")
    public Rest<Boolean> warnLog() {
        helloSender.sendWarnLog();
        return Rest.ok();
    }

    @GetMapping("/log/error")
    public Rest<Boolean> errorLog() {
        helloSender.sendErrorLog();
        return Rest.ok();
    }
}
