package com.apocalypse.example.service.complex;

import java.util.concurrent.CompletableFuture;

/**
 * @author <a href="jingkaihui@adpanshi.com">jingkaihui</a>
 * @Description
 * @date 2019/8/9
 */
public interface AsyncService {

    CompletableFuture<String> asynService() throws InterruptedException;
}
