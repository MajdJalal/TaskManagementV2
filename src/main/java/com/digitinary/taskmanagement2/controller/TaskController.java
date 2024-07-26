package com.digitinary.taskmanagement2.controller;

import com.digitinary.taskmanagement2.constants.TaskManagementConstants;
import com.digitinary.taskmanagement2.dto.ResponseDto;
import com.digitinary.taskmanagement2.dto.TaskDto;
import com.digitinary.taskmanagement2.dto.TaskResponseDto;
import com.digitinary.taskmanagement2.dto.UserDto;
import com.digitinary.taskmanagement2.entity.Task;
import com.digitinary.taskmanagement2.mapper.UserMapper;
import com.digitinary.taskmanagement2.service.ITaskService;
import com.digitinary.taskmanagement2.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Tag(
        name = "CRUD REST APIs for Task",
        description = "CRUD REST APIs for tasks"
)
@RestController
@RequestMapping("/api/taskManagement/v2")
public class TaskController {

    private final ITaskService iTaskService;

    public TaskController(ITaskService iTaskService) {
        this.iTaskService = iTaskService;
    }


    @Operation(
            summary = "Create Task REST API",
            description = "REST API to create a new task"
    )
    @ApiResponse(
            responseCode = "201",
            description = "task created successfully"
    )
    @PostMapping("/task")
    public ResponseEntity<ResponseDto> createTask(@Valid @RequestBody TaskDto taskDto){
        iTaskService.createTask(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(TaskManagementConstants.STATUS_201, TaskManagementConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Delete Task REST API",
            description = "REST API to delete an existing task"
    )
    @ApiResponse(
            responseCode = "204",
            description = "deleted successfully"
    )
    @DeleteMapping("/task")
    public ResponseEntity<ResponseDto> deleteTask(@RequestParam Long taskId){
        iTaskService.deleteTask(taskId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskManagementConstants.STATUS_204, TaskManagementConstants.MESSAGE_204));
    }

    @Operation(
            summary = "Update Task REST API",
            description = "REST API to update an existing task"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Request processed successfully"
    )
    @PutMapping("/task")
    public ResponseEntity<ResponseDto> updateTask(@RequestParam Long taskId,@Valid @RequestBody TaskDto taskDto){
        iTaskService.updateTask(taskId, taskDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskManagementConstants.STATUS_200, TaskManagementConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Get Task REST API",
            description = "REST API to get an existing task"
    )
    @ApiResponse(
            responseCode = "200",
            description = "TaskResponseDto"
    )
    @GetMapping("/task")
    public ResponseEntity<TaskResponseDto> getTask(@RequestParam Long taskId){
        TaskResponseDto taskResponseDto = iTaskService.getTask(taskId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskResponseDto);
    }

    @Operation(
            summary = "Process Task REST API",
            description = "REST API to process existing task"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Request processed successfully"
    )
    @PostMapping("/taskProcessing")
    public ResponseEntity<ResponseDto> processTask(@RequestParam Long taskId) {
        iTaskService.processTask(taskId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskManagementConstants.STATUS_200, TaskManagementConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Get Done Tasks REST API",
            description = "REST API to get done tasks"
    )
    @ApiResponse(
            responseCode = "200",
            description = "list of the done tasks"
    )
    @GetMapping("/doneTasks")
    public ResponseEntity<List<Task>> getDoneTasks() {
        List<Task> doneTasks = iTaskService.getDoneTasks();
        return ResponseEntity.status(HttpStatus.OK)
                .body(doneTasks);
    }

    @Operation(
            summary = "Get Waiting Tasks REST API",
            description = "REST API to get waiting tasks"
    )
    @ApiResponse(
            responseCode = "200",
            description = "list of the waiting tasks"
    )
    @GetMapping("/waitingTasks")
    public ResponseEntity<List<Task>> getWaitingTasks() {
        List<Task> waitingTasks = iTaskService.getWaitingTasks();
        return ResponseEntity.status(HttpStatus.OK)
                .body(waitingTasks);
    }

    @Operation(
            summary = "Get Expired Tasks REST API",
            description = "REST API to get expired tasks"
    )
    @ApiResponse(
            responseCode = "200",
            description = "list of the expired tasks"
    )
    @GetMapping("/expiredTasks")
    public ResponseEntity<List<Task>> getExpiredTasks() {
        List<Task> expiredTasks = iTaskService.getExpiredTasks();
        return ResponseEntity.status(HttpStatus.OK)
                .body(expiredTasks);
    }

    @Operation(
            summary = "Get Tasks Of Specified Priority REST API",
            description = "REST API to get tasks of specified priority"
    )
    @ApiResponse(
            responseCode = "200",
            description = "list of the tasks of specified priority"
    )
    @GetMapping("/tasksOfPriority/{priority}")
    public ResponseEntity<List<Task>> getTasksOfPriority(@PathVariable Integer priority) {
        List<Task> tasks = iTaskService.getTasksOfPriority(priority);
        return ResponseEntity.status(HttpStatus.OK)
                .body(tasks);
    }






}
