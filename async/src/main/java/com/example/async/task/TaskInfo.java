package com.example.async.task;

import java.util.Date;

/**
 * Created by shirukai on 2018/7/31
 * 任务信息
 */
public class TaskInfo {
    private String taskId;
    private TaskStatusEnum status;
    private Date startTime;
    private Date endTime;
    private String totalTime;

    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }



    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime() {
        this.totalTime = (this.endTime.getTime() - this.startTime.getTime()) + "ms";
    }
}