package com.digitinary.taskmanagement2.controller;


import com.digitinary.taskmanagement2.constants.TaskManagementConstants;
import com.digitinary.taskmanagement2.dto.ResponseDto;
import com.digitinary.taskmanagement2.dto.UserDto;
import com.digitinary.taskmanagement2.dto.UserResponseDto;
import com.digitinary.taskmanagement2.mapper.UserMapper;
import com.digitinary.taskmanagement2.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for User",
        description = "CRUD REST APIs for users"
)
@RestController
@RequestMapping("/api/taskManagement/v2")
public class UserController {

    private final IUserService iUserService;

    /**
     * used for dependency injection
     * @param iUserService
     */
    public UserController(IUserService iUserService, UserMapper userMapper) {
        this.iUserService = iUserService;
    }


    @Operation(
            summary = "Create User REST API",
            description = "REST API to create new User"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Created"
    )
    @PostMapping("/user")
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UserDto userDto){
        iUserService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(TaskManagementConstants.STATUS_201, TaskManagementConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Delete User REST API",
            description = "REST API to delete new User"
    )
    @ApiResponse(
            responseCode = "204",
            description = "user was deleted successfully"
    )
    @DeleteMapping("/user")
    public ResponseEntity<ResponseDto> deleteUser(@RequestParam Long userId){
        iUserService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskManagementConstants.STATUS_204, TaskManagementConstants.MESSAGE_204));
    }

    @Operation(
            summary = "Update User REST API",
            description = "REST API to update an existing User"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Request processed successfully"
    )
    @PutMapping("/user")
    public ResponseEntity<ResponseDto> updateUser(@RequestParam Long userId,@Valid @RequestBody UserDto userDto){
        iUserService.updateUser(userId, userDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskManagementConstants.STATUS_200, TaskManagementConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Add Task to  User REST API",
            description = "REST API to Add Task to  User"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Request processed successfully"
    )
    @PostMapping("/userTasks")
    public ResponseEntity<ResponseDto> addTaskToUser(@RequestParam Long userId, @RequestParam Long taskId) {
        iUserService.addTaskToUser(userId, taskId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskManagementConstants.STATUS_200, TaskManagementConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Remove Task from User REST API",
            description = "REST API to remove Task from User"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Request processed successfully"
    )
    @DeleteMapping("/userTasks")
    public ResponseEntity<ResponseDto> removeTaskFromUser(@RequestParam Long userId, @RequestParam Long taskId) {
        iUserService.removeTaskFromUser(userId, taskId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskManagementConstants.STATUS_200, TaskManagementConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Get User REST API",
            description = "REST API Get User details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "userResponseDto"
    )
    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getUser(@RequestParam Long userId) {
        UserResponseDto userResponseDto = iUserService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userResponseDto);
    }



}
