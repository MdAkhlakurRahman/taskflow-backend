package com.taskflow.demo.service;

import com.taskflow.demo.entity.Task;

import java.util.List;

public interface TaskService {
    Task createTaskForUser(Long userId,String title);
    List<Task> getAllTasks(Long userId);
}
