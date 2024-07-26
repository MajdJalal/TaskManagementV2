package com.digitinary.taskmanagement2.controller;


import com.digitinary.taskmanagement2.constants.TaskManagementConstants;
import com.digitinary.taskmanagement2.dto.ProjectDto;
import com.digitinary.taskmanagement2.dto.ProjectResponseDto;
import com.digitinary.taskmanagement2.dto.ResponseDto;
import com.digitinary.taskmanagement2.service.IProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Project",
        description = "CRUD REST APIs for projects"
)
@RestController
@RequestMapping("/api/taskManagement/v2")
public class ProjectController {

    private  final IProjectService iProjectService;

    public ProjectController(IProjectService iProjectService) {
        this.iProjectService = iProjectService;
    }


    @Operation(
            summary = "Create Project REST API",
            description = "REST API to create a new project"
    )
    @ApiResponse(
            responseCode = "201",
            description = "created successfully"
    )
    @PostMapping("/project")
    public ResponseEntity<ResponseDto> createProject(@Valid @RequestBody ProjectDto projectDto) {
            iProjectService.createProject(projectDto);
            return  ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(TaskManagementConstants.STATUS_201, TaskManagementConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Delete Project REST API",
            description = "REST API to delete an existing project"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Request processed successfully"
    )
    @DeleteMapping("/project")
    public ResponseEntity<ResponseDto> deleteProject(@RequestParam Long projectId) {
        iProjectService.deleteProject(projectId);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskManagementConstants.STATUS_200, TaskManagementConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Add Task To Project REST API",
            description = "REST API to add an existing task to an existing project"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Request processed successfully"
    )
    @PostMapping("/projectTasks")
    public ResponseEntity<ResponseDto> addTaskToProject(@RequestParam Long projectId, @RequestParam Long taskId) {
        iProjectService.addTaskToProject(projectId, taskId);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskManagementConstants.STATUS_200, TaskManagementConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Remove Task From Project REST API",
            description = "REST API to remove an existing task from an existing project"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Request processed successfully"
    )
    @DeleteMapping("/projectTasks")
    public ResponseEntity<ResponseDto> removeTaskFromProject(@RequestParam Long projectId, @RequestParam Long taskId) {
        iProjectService.removeTaskFromProject(projectId, taskId);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskManagementConstants.STATUS_200, TaskManagementConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Get Project REST API",
            description = "REST API to get an existing project"
    )
    @ApiResponse(
            responseCode = "200",
            description = "ProjectResponseDto"
    )
    @GetMapping("/project")
    public ResponseEntity<ProjectResponseDto> getProject(@RequestParam Long projectId) {
        ProjectResponseDto projectResponseDto = iProjectService.getProject(projectId);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(projectResponseDto);
    }

    @Operation(
            summary = "Add User To Project REST API",
            description = "REST API to add User To Project"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Request processed successfully"
    )
    @PostMapping("/projectUsers")
    public ResponseEntity<ResponseDto> addUserToProject(@RequestParam Long projectId, @RequestParam Long userId) {
        iProjectService.addUserToProject(projectId, userId);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskManagementConstants.STATUS_200, TaskManagementConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Remove User From Project REST API",
            description = "REST API to remove User from Project"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Request processed successfully"
    )
    @DeleteMapping("/projectUsers")
    public ResponseEntity<ResponseDto> removeUserFromProject(@RequestParam Long projectId, @RequestParam Long userId) {
        iProjectService.removeUserFromProject(projectId, userId);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskManagementConstants.STATUS_200, TaskManagementConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Get Project Completeness Rate REST API",
            description = "REST API to Get Project Completeness Rate"
    )
    @ApiResponse(
            responseCode = "200",
            description = "CompletenessRate Value"
    )
    @GetMapping("/projectCompletenessRate")
    public ResponseEntity<String> getProjectCompletenessRate(@RequestParam Long projectId) {
        String CompletenessRate = iProjectService.getProjectCompletenessRate(projectId);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(CompletenessRate);
    }


}
