package com.taskflow.demo.service;

import com.taskflow.demo.entity.Task;
import com.taskflow.demo.entity.User;
import com.taskflow.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository,UserService userService) {
        this.taskRepository=taskRepository;
        this.userService=userService;
    }

    @Override
    public Task createTaskForUser(Long userId, String title) {
        User user = userService.getUserById(userId);
        Task task = new Task();
        task.setTitle(title);
        task.setUser(user);
        return taskRepository.save(task);
    }


    @Override
    public List<Task> getAllTasks(Long userId){
        userService.getUserById(userId);
        return taskRepository.findByUserId(userId);
    }

}
