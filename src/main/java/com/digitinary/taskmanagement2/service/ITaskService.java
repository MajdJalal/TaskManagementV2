package com.digitinary.taskmanagement2.service;

import com.digitinary.taskmanagement2.dto.TaskDto;
import com.digitinary.taskmanagement2.dto.TaskResponseDto;
import com.digitinary.taskmanagement2.entity.Task;

import java.util.List;

public interface ITaskService {
    void createTask(TaskDto taskDto);

    void deleteTask(Long taskId);

    void updateTask(Long taskId, TaskDto taskDto);

    TaskResponseDto getTask(Long taskId);

    void processTask(Long taskId);

    List<Task> getDoneTasks();

    List<Task> getWaitingTasks();

    List<Task> getExpiredTasks();


    List<Task> getTasksOfPriority(Integer priority);
}
