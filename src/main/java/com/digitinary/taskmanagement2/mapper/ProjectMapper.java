package com.digitinary.taskmanagement2.mapper;

import com.digitinary.taskmanagement2.dto.ProjectDto;
import com.digitinary.taskmanagement2.dto.ProjectResponseDto;
import com.digitinary.taskmanagement2.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toProject(ProjectDto projectDto) {
        return Project.builder()
                .name(projectDto.getName())
                .build();
    }

    public ProjectResponseDto toProjectResponseDto(Project project) {
        return ProjectResponseDto.builder()
                .name(project.getName())
                .tasks(project.getTasks())
                .users(project.getUsers())
                .build();
    }
}
