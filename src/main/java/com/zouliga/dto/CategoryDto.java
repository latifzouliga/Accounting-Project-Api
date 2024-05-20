package com.zouliga.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
    @Schema(hidden = true)
    Long id;

    @Schema(example = "category name")
    @NotBlank(message = "Description is a required field.")
    @Size(
            max = 100, min = 2,
            message = "Description should have 2-100 characters long."
    )
    String description;

    @JsonIgnore
    @Schema(hidden = true)
    CompanyDto company;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    boolean hasProduct; //(only DTO)

}
