package com.digitinary.taskmanagement2.dto;

import com.digitinary.taskmanagement2.entity.Task;
import com.digitinary.taskmanagement2.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.Set;


@Data
@Builder
public class ProjectResponseDto {
    private String name;
    private Set<User> users;
    private Set<Task> tasks;
}
