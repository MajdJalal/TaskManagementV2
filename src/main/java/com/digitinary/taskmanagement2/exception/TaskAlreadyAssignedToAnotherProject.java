package com.digitinary.taskmanagement2.exception;

public class TaskAlreadyAssignedToAnotherProject extends RuntimeException{
    public TaskAlreadyAssignedToAnotherProject(String message) {
        super(message);
    }
}
