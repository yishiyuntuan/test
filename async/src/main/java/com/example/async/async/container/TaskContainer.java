package com.example.async.async.container;

import com.example.async.async.InstacneTask;
import com.example.async.async.SubTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class TaskContainer {
    @Bean
    public Map<Long, InstacneTask> instacneConcurrentHashMap() {
        return new ConcurrentHashMap<>();
    }
    @Bean
    public Map<Long, SubTask> subTaskMap() {
        return new ConcurrentHashMap<>();
    }

}
