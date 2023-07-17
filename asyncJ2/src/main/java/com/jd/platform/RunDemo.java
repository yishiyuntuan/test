package com.jd.platform;

import com.inhuawei.autosec.async.executor.Async;
import com.inhuawei.autosec.async.wrapper.WorkerWrapper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RunDemo {
    public static void main(String[] args) {

        // 线程池
//        ThreadPoolExecutor threadPoolExecutor =
//                new ThreadPoolExecutor(2
//                        , 5
//                        , 30
//                        , TimeUnit.SECONDS
//                        , new ArrayBlockingQueue<Runnable>(5));

        // 任务构建
        TestA testA = new TestA();
        TestB testB = new TestB();
        TestC testC = new TestC();
        WorkerWrapper<String, String> workerWrapper3 = new WorkerWrapper.Builder<String, String>()
                .worker(testC)
                .callback(testC)
                .param("我是参数3")
                .build();

        WorkerWrapper<String, String> workerWrapper2 = new WorkerWrapper.Builder<String, String>()
                .worker(testB)
                .callback(testB)
                .param("我是参数2")
                .next(workerWrapper3)
                .build();
        WorkerWrapper<String, String> workerWrapper1 = new WorkerWrapper.Builder<String, String>()
                .worker(testA)
                .callback(testA)
                .param("我是参数1")
                .next(workerWrapper2,workerWrapper3)
                .build();
        // 异步执行的回调类
        TestAsyncCallback testAsyncCallback = new TestAsyncCallback();
        // 任务执行
        Async.beginWorkAsync(150000, testAsyncCallback, workerWrapper1);
        System.out.println("让我来验证一下是不是真的异步");
    }
}
