package com.cydeo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {

    @Schema(hidden = true)
    private Long id;

    @Schema(example = "123 main st")
    @NotBlank(message = "Address is a required field.")
    @Size(
            max = 100,
            min = 2, message = "Address should have 2-100 characters long."
    )
    private String addressLine1;

    @Schema(example = "123 main st")
    @Size(
            max = 100,
            message = "Address should have maximum 100 characters long."
    )
    private String addressLine2;

    @Schema(example = "Pittsburgh")
    @NotBlank(message = "City is a required field.")
    @Size(
            max = 50, min = 2,
            message = "City should have 2-50 characters long."
    )
    private String city;

    @Schema(example = "Pa")
    @NotBlank(message = "State is a required field.")
    @Size(
            max = 50, min = 2,
            message = "State should have 2-50 characters long."
    )
    private String state;

    @Schema(example = "USA")
    @NotBlank(message = "Country is a required field.")
    @Size(
            max = 50, min = 2,
            message = "Country should have 2-50 characters long."
    )
    private String country;

    @Schema(example = "15555")
    @NotBlank(message = "Zipcode is a required field.")
    @Pattern(
            regexp = "^\\d{5}([-]|\\s*)?(\\d{4})?$",
            message = "Zipcode should have a valid form."
    )
    private String zipCode;

}
