package com.cydeo.dto;

import com.cydeo.enums.CompanyStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class CompanyDto {

    @Schema(hidden = true)
    private Long id;

    @Schema(example = "Green tech")
    @NotBlank(message = "Title is a required field.")
    @Size(max = 100, min = 2, message = "Title should be 2-100 characters long.")
    private String title;

    @Schema(example = "1-xxx-xxx-xxxx")
    @Pattern(
            regexp = "^1-[0-9]{3}?-[0-9]{3}?-[0-9]{4}$",
            message = "Phone is required field and may be in any valid phone number format."
    )
    private String phone;

    @Schema(example = "www.example.com")
    @NotBlank(message = "Website is a required field.")
    @Pattern(
            regexp = "^(https?://)?([a-zA-Z0-9\\-]+\\.)+[a-zA-Z]{2,5}(/[a-zA-Z0-9\\-._~:/?#\\@!$&'()*+,;=]*)?$",
            message = "Website should have a valid format."
    )
    private String website;

    @NotNull
    @Valid
    private AddressDto address;
    private CompanyStatus companyStatus;


//    String urlPattern = new RegExp('^(https?:\/\/)?'+ // validate protocol
//            '((([a-z\d]([a-z\d-][a-z\d]))\.)+[a-z]{2,}|'+ // validate domain name
//            '((\d{1,3}\.){3}\d{1,3}))'+ // validate OR ip (v4) address
//            '(\:\d+)?(\/[-a-z\d%.~+])'+ // validate port and path
//            '(\?[;&a-z\d%.~+=-])?'+ // validate query string
//            '(\#[-a-z\d_])?$','i'); // validate fragment locator
}
