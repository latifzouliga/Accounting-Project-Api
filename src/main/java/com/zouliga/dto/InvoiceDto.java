package com.zouliga.dto;

import com.zouliga.enums.InvoiceStatus;
import com.zouliga.enums.InvoiceType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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
