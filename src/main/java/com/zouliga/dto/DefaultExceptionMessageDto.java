package com.zouliga.dto;

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