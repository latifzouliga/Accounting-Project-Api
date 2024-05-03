package com.cydeo.dto;

import lombok.Builder;

@Builder
public record CategoryDto(
        Long id,
        String description,
        CompanyDto company,
        boolean hasProduct //(only DTO)
) {
}
