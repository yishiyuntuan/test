package com.jd.platform;

import com.example.async.async.callback.IGroupCallback;
import com.example.async.async.executor.Async;
import com.example.async.async.wrapper.WorkerWrapper;

import java.util.List;

public class TestAsyncCallback implements IGroupCallback {
    @Override
    public void success(List<WorkerWrapper> workerWrappers) {
        System.out.println("回调执行 -----  "+workerWrappers.size());
        for (WorkerWrapper workerWrapper : workerWrappers) {
            System.out.println(workerWrapper.getWorkResult());
        }
        System.out.println("回调执行 ----- success ");
        Async.shutDown();
    }

    @Override
    public void failure(List<WorkerWrapper> workerWrappers, Exception e) {
        System.out.println("回调执行 -----  ");
        for (WorkerWrapper workerWrapper : workerWrappers) {
            System.out.println(workerWrapper.getWorkResult());
        }
        System.out.println("回调执行 ----- failure ");
        Async.shutDown();
    }
}

