package com.digitinary.taskmanagement2.exception;

public class TaskNotAssignedToUser extends RuntimeException{
    public TaskNotAssignedToUser(String message) {
        super(message);
    }
}
