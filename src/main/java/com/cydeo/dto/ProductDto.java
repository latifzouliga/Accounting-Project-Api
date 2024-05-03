package com.cydeo.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDto(
        Long id,
        Integer quantity,
        BigDecimal price,
        Integer tax,
        BigDecimal total,
        BigDecimal profitLoss,
        Integer remainingQuantity,
        InvoiceDto invoice,
        ProductDto product
) {
}
