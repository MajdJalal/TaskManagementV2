package com.digitinary.taskmanagement2.dto;

import com.digitinary.taskmanagement2.entity.Project;
import com.digitinary.taskmanagement2.entity.User;
import com.digitinary.taskmanagement2.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class TaskResponseDto {

    private String title;
    private String description;
    private TaskStatus status;
    private int priority;
    private LocalDate dueDate;
    private User user;
    private Project project;
}
