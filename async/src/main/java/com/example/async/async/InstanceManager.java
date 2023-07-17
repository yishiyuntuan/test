package com.example.async.async;

import com.example.async.async.executor.Async;
import com.example.async.async.wrapper.WorkerWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;


@Service
public class InstanceManager {

    @Resource
    private ConcurrentHashMap<Long,InstacneTask> instacneConcurrentHashMap;
    public Long submit(){
        InstacneTask instacneTask = new InstacneTask();


        instacneTask.setId(System.currentTimeMillis());

        // 一个实例有多个并行执行的任务
        WorkerWrapper<Long, String> build = new WorkerWrapper.Builder<Long, String>()
                .worker(instacneTask)
                .callback(instacneTask)
                .build();
        instacneConcurrentHashMap.put(instacneTask.getId(),instacneTask);

        Async.beginWorkAsync(3600*1000,instacneTask,build);

        return instacneTask.getId();
    }

}
