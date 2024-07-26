package com.digitinary.taskmanagement2.exception;

/**
 * invalid name or email of the user to add
 */
public class InvalidUserDetailsException extends RuntimeException{
    public InvalidUserDetailsException(String message) {
        super(message);
    }
}
