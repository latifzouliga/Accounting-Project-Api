package com.cydeo.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(String.format("User %s could not be found", message));
    }
}
