package com.zouliga.exception;


import com.zouliga.annotation.DefaultExceptionMessage;
import com.zouliga.dto.DefaultExceptionMessageDto;
import com.zouliga.entity.ResponseWrapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseWrapper> resourceNotFoundException(ResourceNotFoundException se) {
        String message = se.getMessage();
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .success(false)
                        .code(HttpStatus.NOT_FOUND.value())
                        .message(message)
                        .build(),
                HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ResponseWrapper> serviceException(ServiceException se) {
        String message = se.getMessage();
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .success(false)
                        .code(HttpStatus.CONFLICT.value())
                        .message(message)
                        .build(),
                HttpStatus.CONFLICT);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseWrapper> accessDeniedException(AccessDeniedException se) {
        String message = se.getMessage();
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .success(false)
                        .code(HttpStatus.FORBIDDEN.value())
                        .message(message)
                        .build(),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(
                        ResponseWrapper.builder()
                                .success(false)
                                .message("Validation error")
                                .data(errors)
                                .build());
    }

    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class, BadCredentialsException.class})
    public ResponseEntity<ResponseWrapper> genericException(HandlerMethod handlerMethod) {

        // the first condition will execute if the exception happens in methods that are annotated with @DefaultExceptionMessage
        Optional<DefaultExceptionMessageDto> defaultMessage = getMessageFromAnnotation(handlerMethod.getMethod());

        if (defaultMessage.isPresent() && !ObjectUtils.isEmpty(defaultMessage.get().getMessage())) {
            ResponseWrapper response = ResponseWrapper
                    .builder()
                    .success(false)
                    .message(defaultMessage.get().getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .success(false)
                        .message(handlerMethod.getShortLogMessage())
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Optional<DefaultExceptionMessageDto> getMessageFromAnnotation(Method method) {
        DefaultExceptionMessage defaultExceptionMessage = method.getAnnotation(DefaultExceptionMessage.class);
        if (defaultExceptionMessage != null) {
            DefaultExceptionMessageDto defaultExceptionMessageDto = DefaultExceptionMessageDto
                    .builder()
                    .message(defaultExceptionMessage.defaultMessage())
                    .build();
            return Optional.of(defaultExceptionMessageDto);
        }
        return Optional.empty();
    }
}