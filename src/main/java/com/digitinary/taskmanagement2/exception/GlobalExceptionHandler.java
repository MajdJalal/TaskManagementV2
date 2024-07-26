package com.digitinary.taskmanagement2.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<String> handleNotFoundUserException(NotFoundUserException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidUserDetailsException.class)
    public ResponseEntity<String> handleUsernameAlreadyExistsException(InvalidUserDetailsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundTaskException.class)
    public ResponseEntity<String> handleNotFoundTaskException(NotFoundTaskException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(TaskAlreadyAssignedToAnotherUser.class)
    public ResponseEntity<String> handleTaskAlreadyAssignedToAnotherUser(TaskAlreadyAssignedToAnotherUser e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(TaskNotAssignedToUser.class)
    public ResponseEntity<String> handleTaskNotAssignedToUser(TaskNotAssignedToUser e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundProjectException.class)
    public ResponseEntity<String> handleNotFoundProjectException(NotFoundProjectException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(TaskAlreadyAssignedToAnotherProject.class)
    public ResponseEntity<String> handleTaskAlreadyAssignedToAnotherProject(TaskAlreadyAssignedToAnotherProject e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(TaskNotAssignedToProject.class)
    public ResponseEntity<String> handleTaskNotAssignedToProject(TaskNotAssignedToProject e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(UserNotAssignedToThisProject.class)
    public ResponseEntity<String> handleUserNotAssignedToThisProject(UserNotAssignedToThisProject e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidTaskDetailsException.class)
    public ResponseEntity<String> handleInvalidTaskDetailsException(InvalidTaskDetailsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidProjectDetailsException.class)
    public ResponseEntity<String> handleInvalidProjectDetailsException(InvalidProjectDetailsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }







}
