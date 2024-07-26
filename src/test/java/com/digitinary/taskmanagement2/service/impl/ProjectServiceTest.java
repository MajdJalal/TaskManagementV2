package com.digitinary.taskmanagement2.service.impl;

import com.digitinary.taskmanagement2.dto.ProjectDto;
import com.digitinary.taskmanagement2.entity.Project;
import com.digitinary.taskmanagement2.entity.Task;
import com.digitinary.taskmanagement2.exception.InvalidProjectDetailsException;
import com.digitinary.taskmanagement2.exception.NotFoundProjectException;
import com.digitinary.taskmanagement2.exception.TaskAlreadyAssignedToAnotherProject;
import com.digitinary.taskmanagement2.mapper.ProjectMapper;
import com.digitinary.taskmanagement2.repository.ProjectRespository;
import com.digitinary.taskmanagement2.repository.TaskRespository;
import com.digitinary.taskmanagement2.repository.UserRespository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {
    @Mock
    private ProjectRespository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private TaskRespository taskRepository;

    @Mock
    private UserRespository userRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProject() {
        ProjectDto projectDto = ProjectDto.builder().build();
        Project project = new Project();
        when(projectMapper.toProject(projectDto)).thenReturn(project);

        projectService.createProject(projectDto);

        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void testCreateProject_ThrowsInvalidProjectDetailsException() {
        ProjectDto projectDto = ProjectDto.builder().build();
        Project project = new Project();
        when(projectMapper.toProject(projectDto)).thenReturn(project);
        doThrow(new RuntimeException("Error saving project")).when(projectRepository).save(project);

        assertThrows(InvalidProjectDetailsException.class, () -> {
            projectService.createProject(projectDto);
        });

    }


    @Test
    public void testAddTaskToProject_ThrowsNotFoundProjectException() {
        Long projectId = 1L;
        Long taskId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(NotFoundProjectException.class, () -> {
            projectService.addTaskToProject(projectId, taskId);
        });

    }


    @Test
    public void testAddTaskToProject_ThrowsTaskAlreadyAssignedToAnotherProject() {
        Long projectId = 1L;
        Long taskId = 1L;
        Project project = new Project();
        project.setId(projectId);
        Task task = new Task();
        task.setProject(Project.builder().id(2L).build()); // Task already assigned to another project
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        assertThrows(TaskAlreadyAssignedToAnotherProject.class, () -> {
            projectService.addTaskToProject(projectId, taskId);
        });

    }



}