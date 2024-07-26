package com.digitinary.taskmanagement2.exception;

public class TaskAlreadyAssignedToAnotherUser extends RuntimeException{
    public TaskAlreadyAssignedToAnotherUser(String message) {
        super(message);
    }
}
