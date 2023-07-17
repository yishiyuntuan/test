package com.jd.platform;

import com.inhuawei.autosec.async.callback.IGroupCallback;
import com.inhuawei.autosec.async.executor.Async;
import com.inhuawei.autosec.async.wrapper.WorkerWrapper;

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

