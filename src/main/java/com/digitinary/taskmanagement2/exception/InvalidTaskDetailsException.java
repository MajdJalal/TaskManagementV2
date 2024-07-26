package com.digitinary.taskmanagement2.exception;

public class InvalidTaskDetailsException extends RuntimeException{
    public InvalidTaskDetailsException(String message) {
        super(message);
    }
}
