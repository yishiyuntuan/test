package com.jd.platform;

import com.inhuawei.autosec.async.callback.ICallback;
import com.inhuawei.autosec.async.callback.IWorker;
import com.inhuawei.autosec.async.executor.timer.SystemClock;
import com.inhuawei.autosec.async.worker.WorkResult;
import com.inhuawei.autosec.async.wrapper.WorkerWrapper;

import java.util.Map;

public class TestC implements IWorker<String, String>, ICallback<String, String> {
    @Override
    public String action(String object, Map<String, WorkerWrapper> allWrappers) {

        try {
            Thread.sleep(9999);
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