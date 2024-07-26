package com.digitinary.taskmanagement2.service;

import com.digitinary.taskmanagement2.dto.ProjectDto;
import com.digitinary.taskmanagement2.dto.ProjectResponseDto;

public interface IProjectService {
    void createProject(ProjectDto projectDto);

    void addTaskToProject(Long projectId, Long taskId);

    ProjectResponseDto getProject(Long projectId);

    void removeTaskFromProject(Long projectId, Long taskId);

    void deleteProject(Long projectId);

    void addUserToProject(Long projectId, Long userId);

    String getProjectCompletenessRate(Long projectId);

    void removeUserFromProject(Long projectId, Long userId);
}
