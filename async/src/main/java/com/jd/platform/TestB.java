package com.jd.platform;

import com.example.async.async.callback.ICallback;
import com.example.async.async.callback.IWorker;
import com.example.async.async.executor.timer.SystemClock;
import com.example.async.async.worker.WorkResult;
import com.example.async.async.wrapper.WorkerWrapper;

import java.util.Map;

public class TestB implements IWorker<String, String>, ICallback<String, String> {
    @Override
    public String action(String object, Map<String, WorkerWrapper> allWrappers) {

        try {
            Thread.sleep(19999);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("result = " + SystemClock.now() + "---param = " + object +Thread.currentThread().getName());
        return "action...";
    }

    @Override
    public void result(boolean success, String param, WorkResult<String> workResult) {
        System.out.println("...result..."+param+"----"+success+Thread.currentThread().getName());

    }
}

