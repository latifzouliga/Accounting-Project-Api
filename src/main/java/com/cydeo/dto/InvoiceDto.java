package com.cydeo.dto;

import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record InvoiceDto(
        Long id,
        String invoiceNo,
        InvoiceStatus invoiceStatus,
        InvoiceType invoiceType,
        LocalDate date,
        CompanyDto company,
        ClientVendorDto clientVendor,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        BigDecimal price,  //(only in Dto)
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        BigDecimal tax,   //(only in Dto)
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        BigDecimal total   //(only in Dto)
){}
