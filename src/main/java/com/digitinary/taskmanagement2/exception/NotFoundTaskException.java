package com.digitinary.taskmanagement2.exception;

public class NotFoundTaskException extends  RuntimeException{
    public NotFoundTaskException(String message) {
        super(message);
    }
}
