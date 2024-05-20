package com.zouliga.dto;

import com.zouliga.enums.ProductUnit;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    @Schema(description = "Product name")
    @NotBlank(message = "Product Name is a required field.")
    @Size(max = 100, min = 2, message = "Product Name should be 2-100 characters long.")
    private String name;

    private int quantityInStock;

    @NotNull(message = "Low Limit Alert is a required field.")
    @Range(min = 1, message = "Low Limit Alert should be at least 1.")
    private int lowLimitAlert;

    @Schema(description = "LBS, GALLON, PCS, KG, METER, INCH, FEET")
    @NotNull(message = "Product Unit is a required field.")
    private ProductUnit productUnit;

    @NotNull(message = "Category is a required field.")
    private CategoryDto category;
}
