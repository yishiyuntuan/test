package com.example.async.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by shirukai on 2018/7/31
 * 异步任务执行器
 */
@Component
@Slf4j
public class AsyncTaskExecutor {
    @Async
    public void executor(AsyncTaskConstructor asyncTaskGenerator) {
        log.info("AsyncTaskExecutor is executing async task:{}",asyncTaskGenerator);
        asyncTaskGenerator.async();
    }
}