package com.apocalypse.example.sender;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/5/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloSenderTest {

    @Autowired
    private HelloSender helloSender;

    @Test
    public void send() {
        for (int i = 0; i < 10; i++) {
            helloSender.send(i);
        }
    }
}