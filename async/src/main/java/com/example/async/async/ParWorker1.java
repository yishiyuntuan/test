package com.example.async.async;


import com.example.async.async.callback.IWorker;
import com.example.async.async.executor.timer.SystemClock;
import com.example.async.async.wrapper.WorkerWrapper;

import java.util.Map;

/**
 * @author wuweifeng wrote on 2019-11-20.
 */
public class ParWorker1 implements IWorker<Long, String>{
    private long sleepTime = 10000;

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public String action(Long object, Map<String, WorkerWrapper> allWrappers) {
        SubTask subTask = new SubTask();
        subTask.doTask("1");
        return "result = " + SystemClock.now() + "---param = " + object + " from 1";
    }

    @Override
    public String defaultValue() {
        return "worker1--default";
    }


}
