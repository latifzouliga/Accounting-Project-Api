package com.cydeo.dto;

import com.cydeo.enums.ClientVendorType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ClientVendorDto(
        Long id,
        String clientVendorName,
        String phone,
        String website,
        ClientVendorType clientVendorType,
        AddressDto address,
        CompanyDto company,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        boolean hasInvoice //(only DTO)
) {
}
