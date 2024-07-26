package com.digitinary.taskmanagement2.exception;

public class TaskNotAssignedToProject extends RuntimeException{
    public TaskNotAssignedToProject(String message) {
        super(message);
    }
}
