package com.cydeo.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(String.format(message));
    }
}
