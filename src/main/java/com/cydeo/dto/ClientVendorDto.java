package com.cydeo.dto;

import com.cydeo.enums.ClientVendorType;
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
        boolean hasInvoice //(only DTO)
) {
}
