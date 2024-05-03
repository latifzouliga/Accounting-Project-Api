package com.cydeo.dto;

import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
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
        BigDecimal price,  //(only in Dto)
        BigDecimal tax,   //(only in Dto)
        BigDecimal total   //(only in Dto)
){}
