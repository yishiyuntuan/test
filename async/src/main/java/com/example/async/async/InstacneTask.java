package com.example.async.async;

import com.example.async.async.callback.ICallback;
import com.example.async.async.callback.IGroupCallback;
import com.example.async.async.callback.IWorker;
import com.example.async.async.executor.Async;
import com.example.async.async.executor.timer.SystemClock;
import com.example.async.async.worker.WorkResult;
import com.example.async.async.wrapper.WorkerWrapper;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


@Data
public class InstacneTask implements IWorker<Long, String>, ICallback<Long, String>, IGroupCallback{
    private Long id;
    private Map<Long, WorkerWrapper> taskMap;
    private ExecutorService executorService=(ThreadPoolExecutor) Executors.newCachedThreadPool();;

    public InstacneTask() {
        taskMap=new HashMap<>();
        ParWorker1 parWorker1 = new ParWorker1();
        ParWorker2 parWorker2 = new ParWorker2();
        ParWorker3 parWorker3 = new ParWorker3();
        WorkerWrapper<Long, String> build1 = new WorkerWrapper.Builder<Long, String>()
                .worker(parWorker1)
                .callback(this)
                .build();
        WorkerWrapper<Long, String> build2 = new WorkerWrapper.Builder<Long, String>()
                .worker(parWorker2)
                .callback(this)
                .build();
        WorkerWrapper<Long, String> build3 = new WorkerWrapper.Builder<Long, String>()
                .worker(parWorker3)
                .callback(this)
                .build();
        taskMap.put(1L,build1);
        taskMap.put(2L,build2);
        taskMap.put(3L,build3);
    }

    @SneakyThrows
    @Override
    public String action(Long object, Map<String, WorkerWrapper> allWrappers) {
        // 并行执行taskMap中的任务
        List<WorkerWrapper> list = taskMap.values().stream().toList();
        Async.beginWork((long) (3600 * 1000),executorService,list);
        list.forEach(x->{
            System.out.println(x.getWorkResult());
        });
        return "aaaaa";
    }

    @Override
    public void result(boolean success, Long param, WorkResult<String> workResult) {
        if (success) {
            System.out.println("callback worker1 success--" + SystemClock.now() + "----" + workResult.getResult()
                    + "-threadName:" +Thread.currentThread().getName());
        } else {
            System.err.println("callback worker1 failure--" + SystemClock.now() + "----"  + workResult.getResult()
                    + "-threadName:" +Thread.currentThread().getName());
        }
    }


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
