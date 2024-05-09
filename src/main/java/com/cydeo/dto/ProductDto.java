package com.cydeo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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
