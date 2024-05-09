package com.cydeo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AddressDto(
    Long id,
    String addressLine1,
    String addressLine2,
    String city,
    String state,
    String country,
    String zipCode
){}
