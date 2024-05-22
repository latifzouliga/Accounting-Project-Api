package com.zouliga.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(String.format("Resource %s could not be found", message));
    }
    public ResourceNotFoundException() {
        super();
    }

}
