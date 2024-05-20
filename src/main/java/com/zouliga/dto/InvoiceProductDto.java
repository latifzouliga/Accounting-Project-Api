package com.zouliga.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceProductDto {
        Long id;
        Integer quantity;
        BigDecimal price;
        Integer tax;
        BigDecimal total;
        BigDecimal profitLoss;
        Integer remainingQuantity;
        InvoiceDto invoice;
        InvoiceProductDto product;

}
