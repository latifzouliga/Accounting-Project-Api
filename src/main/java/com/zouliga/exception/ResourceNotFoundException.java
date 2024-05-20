package com.zouliga.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(String.format("Resource %s could not be found", message));
    }
}
