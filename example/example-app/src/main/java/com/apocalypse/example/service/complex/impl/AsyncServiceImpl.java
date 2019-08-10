package com.apocalypse.example.service.complex.impl;

import com.apocalypse.example.service.complex.AsyncService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="jingkaihui@adpanshi.com">jingkaihui</a>
 * @Description
 * @date 2019/8/9
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    @Async("applicationTaskExecutor")
    @Override
    public CompletableFuture<String> asynService() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        System.out.println(Thread.currentThread().getName());
        String funnyName = new Faker(Locale.getDefault()).funnyName().name();
        return CompletableFuture.completedFuture(funnyName);
    }
}
