package com.example.async.async;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class SubTask {
    private String name;
    private String pipelineId;
    public ReentrantLock lock = new ReentrantLock();
    private Thread currentThread;


    // 要执行的任务
    public void doTask(String pipelineId) {
        System.out.println("doTask");

        this.pipelineId = "pipelineId" + pipelineId;
        System.out.println(pipelineId);

        // 等待外部通知
        // 通知到来后，执行任务
        //  获取当前线程的实例
        this.currentThread = Thread.currentThread();
        ConcurrentHashMap<String,SubTask> sub=SpringBootBeanUtil.getBean("subTaskMap",ConcurrentHashMap.class);
        sub.put(this.pipelineId,this);
        LockSupport.park();


        System.out.println("测试执行");
        // 睡眠20s
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("测试执行结束");
    }

    // 唤醒当前线程
    public void notifyTask() {
        System.out.println("notifyTask");
        LockSupport.unpark(this.currentThread);
    }

}
