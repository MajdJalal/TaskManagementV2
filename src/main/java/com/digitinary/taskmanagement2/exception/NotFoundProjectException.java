package com.digitinary.taskmanagement2.exception;

public class NotFoundProjectException extends RuntimeException{
    public NotFoundProjectException(String message) {
        super(message);
    }
}
