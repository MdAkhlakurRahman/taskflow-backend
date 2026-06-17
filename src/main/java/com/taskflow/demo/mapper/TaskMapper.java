package com.taskflow.demo.mapper;

import com.taskflow.demo.dto.TaskRequestDTO;
import com.taskflow.demo.dto.TaskResponseDTO;
import com.taskflow.demo.entity.Task;

public class TaskMapper {

    public static TaskResponseDTO taskToTaskResponseDTO(Task task){
        return new TaskResponseDTO(task.getId(), task.getTitle());
    }

    public static Task taskRequestDTOToTask(TaskRequestDTO taskRequestDTO){
        Task task = new Task();
        task.setTitle(taskRequestDTO.title());
        return task;
    }
}
