package com.digitinary.taskmanagement2.dto;


import com.digitinary.taskmanagement2.entity.Project;
import com.digitinary.taskmanagement2.entity.Task;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserResponseDto {
    private String name;
    private String email;
    private Set<Task> tasks;
    private Set<Project> projects;
}
