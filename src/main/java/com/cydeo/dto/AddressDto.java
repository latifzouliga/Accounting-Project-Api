package com.cydeo.dto;

import lombok.Builder;

@Builder
public record AddressDto(
    Long id,
    String addressLine1,
    String addressLine2,
    String city,
    String state,
    String country,
    String zipCode
){}
