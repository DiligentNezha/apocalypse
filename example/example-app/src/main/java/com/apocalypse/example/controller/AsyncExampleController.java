package com.apocalypse.example.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.example.service.complex.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/4
 */
@Slf4j
@RestController
@RequestMapping("/async")
public class AsyncExampleController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/callable")
    public Callable<Rest<BaseResponse>> testCallable() {
        log.info("Controller开始执行！");
        Callable<Rest<BaseResponse>> callable = () -> {
            Thread.sleep(5000);

            log.info("实际工作执行完成！");

            return Rest.vector("content", "我是异步返回的数据", String.class);
        };
        log.info("Controller执行结束！");
        return callable;
    }

    @GetMapping("/deferredResult")
    public DeferredResult<Rest<BaseResponse>> deferredResult() {
        log.info("Controller开始执行！");
        DeferredResult<Rest<BaseResponse>> deferredResult = new DeferredResult<>();
        ThreadUtil.execAsync(() -> {
            ThreadUtil.sleep(3000);
            return Rest.vector("content", "我是异步返回的数据", String.class);
        });
        log.info("Controller执行结束！");
        return deferredResult;
    }

    @GetMapping("/sseEmitter")
    public SseEmitter sseEmitter() {
        log.info("Controller开始执行！");
        SseEmitter sseEmitter = new SseEmitter();
        ThreadUtil.execAsync(() -> {
            try {
                sseEmitter.send(Rest.success("我是异步返回的数据"), MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE));
                ThreadUtil.sleep(3000);
                sseEmitter.complete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        log.info("Controller执行结束！");
        return sseEmitter;
    }

    @GetMapping("/asynService")
    public DeferredResult<Rest<BaseResponse>> asynService() {
        DeferredResult<Rest<BaseResponse>> deferredResult = new DeferredResult<>();
        ThreadUtil.execAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                CompletableFuture<String> firstFunnyName = asyncService.asynService();
                CompletableFuture<String> secondFunnyName = asyncService.asynService();
                CompletableFuture<String> thirdFunnyName = asyncService.asynService();
                CompletableFuture.allOf(firstFunnyName, secondFunnyName, thirdFunnyName).join();
                List content = Arrays.asList(firstFunnyName.get(), secondFunnyName.get(), thirdFunnyName.get());
                deferredResult.setResult(Rest.vector("content", content, List.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return deferredResult;
    }
}
