package com.digitinary.taskmanagement2.exception;

public class UserNotAssignedToThisProject extends RuntimeException{
    public UserNotAssignedToThisProject(String message) {
        super(message);
    }
}
