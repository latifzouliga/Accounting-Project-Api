package com.cydeo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryDto(
        Long id,
        String description,
        CompanyDto company,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        boolean hasProduct //(only DTO)
) {
}
