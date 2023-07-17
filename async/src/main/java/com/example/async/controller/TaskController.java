package com.example.async.controller;


import com.example.async.async.InstacneTask;
import com.example.async.async.InstanceManager;
import com.example.async.async.SpringBootBeanUtil;
import com.example.async.async.SubTask;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class TaskController {
    //注入异步任务管理器
    @Resource
    InstanceManager manager;

    @Resource
    private Map<Long, InstacneTask> instacneConcurrentHashMap;


    @RequestMapping(value = "/startTask", method = RequestMethod.GET)
    public Long startAsyncTask() {
        //调用任务管理器中的submit去提交一个异步任务
        return manager.submit();

    }

    @GetMapping("/getTask")
    public void getTask() {
        ConcurrentHashMap<String,SubTask> subTaskMap = SpringBootBeanUtil.getBean("subTaskMap", ConcurrentHashMap.class);
        subTaskMap.get("pipelineId1").notifyTask();


    }

    @RequestMapping(value = "/getTaskStatus", method = RequestMethod.GET)
    public Set<Long> getTaskStatus() {
//         return Async.forParamUseWrappers.get("last").getWorkResult().getResultState();
        return instacneConcurrentHashMap.keySet();
    }
}
