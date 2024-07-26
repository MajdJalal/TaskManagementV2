package com.digitinary.taskmanagement2.mapper;

import com.digitinary.taskmanagement2.dto.TaskDto;
import com.digitinary.taskmanagement2.dto.TaskResponseDto;
import com.digitinary.taskmanagement2.entity.Task;
import com.digitinary.taskmanagement2.runnable.RunnableTask;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {


    public Task toTask(TaskDto taskDto){
        return Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .status(taskDto.getStatus())
                .priority(taskDto.getPriority())
                .dueDate(taskDto.getDueDate())
                .build();
    }

    public TaskDto toTaskDto(Task task){
        return TaskDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .build();
    }


    public TaskResponseDto toTaskResponseDto(Task task) {
        return TaskResponseDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .user(task.getUser())
                .project(task.getProject())
                .build();
    }

    public RunnableTask toRunnableTask(Task task) {
        return RunnableTask.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .build();
    }
}
