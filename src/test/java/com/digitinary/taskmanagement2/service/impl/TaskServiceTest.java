package com.digitinary.taskmanagement2.service.impl;

import com.digitinary.taskmanagement2.dto.TaskDto;
import com.digitinary.taskmanagement2.dto.TaskResponseDto;
import com.digitinary.taskmanagement2.entity.Task;
import com.digitinary.taskmanagement2.enums.TaskStatus;
import com.digitinary.taskmanagement2.exception.InvalidTaskDetailsException;
import com.digitinary.taskmanagement2.exception.NotFoundTaskException;
import com.digitinary.taskmanagement2.mapper.TaskMapper;
import com.digitinary.taskmanagement2.repository.TaskRespository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRespository taskRepository;

    @Mock
    private TaskMapper taskMapper;



    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTask() {
        TaskDto taskDto = TaskDto.builder().build();
        Task task = new Task();
        when(taskMapper.toTask(taskDto)).thenReturn(task);

        taskService.createTask(taskDto);

        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testCreateTask_ThrowsInvalidTaskDetailsException() {
        TaskDto taskDto = TaskDto.builder().build();
        Task task = new Task();
        when(taskMapper.toTask(taskDto)).thenReturn(task);
        doThrow(new RuntimeException("Error saving task")).when(taskRepository).save(task);

        assertThrows(InvalidTaskDetailsException.class, () -> {
            taskService.createTask(taskDto);
        });

    }

    @Test
    public void testDeleteTask() {
        Long taskId = 1L;
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    public void testDeleteTask_ThrowsNotFoundTaskException() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(NotFoundTaskException.class, () -> {
            taskService.deleteTask(taskId);
        });

    }

    @Test
    public void testUpdateTask() {
        Long taskId = 1L;
        TaskDto taskDto = TaskDto.builder().build();
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.updateTask(taskId, taskDto);

        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testUpdateTask_ThrowsNotFoundTaskException() {
        Long taskId = 1L;
        TaskDto taskDto = TaskDto.builder().build();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(NotFoundTaskException.class, () -> {
            taskService.updateTask(taskId, taskDto);
        });
    }

    @Test
    public void testGetTask() {
        Long taskId = 1L;
        Task task = new Task();
        TaskResponseDto taskResponseDto =  TaskResponseDto.builder().build();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.toTaskResponseDto(task)).thenReturn(taskResponseDto);

        TaskResponseDto result = taskService.getTask(taskId);

        assertEquals(taskResponseDto, result);
    }



    @Test
    public void testGetDoneTasks() {
        Task task = new Task();
        List<Task> tasks = List.of(task);
        when(taskRepository.findAllByStatus(TaskStatus.DONE)).thenReturn(tasks);

        List<Task> result = taskService.getDoneTasks();

        assertEquals(tasks, result);
    }

    @Test
    public void testGetWaitingTasks() {
        Task task = new Task();
        List<Task> tasks = List.of(task);
        when(taskRepository.findAllByStatus(TaskStatus.WAITING)).thenReturn(tasks);

        List<Task> result = taskService.getWaitingTasks();

        assertEquals(tasks, result);
    }



    @Test
    public void testGetTasksOfPriority() {
        Integer priority = 1;
        Task task = new Task();
        List<Task> tasks = List.of(task);
        when(taskRepository.findAllByPriority(priority)).thenReturn(tasks);

        List<Task> result = taskService.getTasksOfPriority(priority);

        assertEquals(tasks, result);
    }

}