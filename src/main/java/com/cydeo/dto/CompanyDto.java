package com.cydeo.dto;

import com.cydeo.enums.CompanyStatus;
import lombok.Builder;

@Builder
public record CompanyDto(
        Long id,
        String title,
        String phone,
        String website,
        AddressDto address,
        CompanyStatus companyStatus
) {
}
