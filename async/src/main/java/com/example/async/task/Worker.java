package com.example.async.task;

public abstract class Worker implements AsyncTaskConstructor {
    protected TaskInfo taskInfo;

    @Override
    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo=taskInfo;
    }
}
