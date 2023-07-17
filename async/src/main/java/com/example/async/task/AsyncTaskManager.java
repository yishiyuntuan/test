package com.example.async.task;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shirukai on 2018/7/31
 * 异步任务管理器
 */
@Component
public class AsyncTaskManager {

    // 所以的任务
    private Map<String, TaskInfo> taskContainer = new HashMap<>(16);
    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private final AtomicInteger stepCounter = new AtomicInteger(0);
    @Resource
    AsyncTaskExecutor asyncTaskExecutor;

    /**
     * 初始化任务
     * @return taskInfo
     */
    public TaskInfo initTask() {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskId(getTaskId());
        taskInfo.setStatus(TaskStatusEnum.STARTED);
        taskInfo.setStartTime(new Date());
        setTaskInfo(taskInfo);
        return taskInfo;
    }

    /**
     * 初始化任务
     * @param asyncTaskConstructor 异步任务构造器
     * @return taskInfo
     */
    public TaskInfo submit(AsyncTaskConstructor asyncTaskConstructor) {
        TaskInfo info = initTask();
        asyncTaskConstructor.setTaskInfo(info);
        asyncTaskExecutor.executor(asyncTaskConstructor);
        return info;
    }
    /**
     * 保存任务信息
     *
     * @param taskInfo 任务信息
     */
    public void setTaskInfo(TaskInfo taskInfo) {
        taskContainer.put(taskInfo.getTaskId(), taskInfo);
    }

    /**
     * 获取任务信息
     *
     * @param taskId 任务ID
     * @return
     */
    public TaskInfo getTaskInfo(String taskId) {
        return taskContainer.get(taskId);
    }
    /**
     * 获取任务状态
     *
     * @param taskId 任务ID
     * @return
     */
    public TaskStatusEnum getTaskStatus(String taskId) {
        return getTaskInfo(taskId).getStatus();
    }
    /**
     * 生成任务ID
     *
     * @return taskId
     */
    public String getTaskId() {
        return UUID.randomUUID().toString();
    }
}