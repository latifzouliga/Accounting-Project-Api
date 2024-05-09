package com.cydeo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DefaultExceptionMessageDto {
    private String message;
    public DefaultExceptionMessageDto(String message) {
        this.message = message;
    }
}