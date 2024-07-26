package com.digitinary.taskmanagement2.exception;

public class NotFoundUserException extends  RuntimeException{

    public NotFoundUserException(String message) {
        super(message);
    }
}
