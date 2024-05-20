package com.zouliga.dto;

import com.zouliga.enums.ClientVendorType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ClientVendorDto {

    @Schema(hidden = true)
    private Long id;

    @Schema(example = "Green tech")
    @NotBlank(message = "Company Name is a required field.")
    @Size(
            min = 2,
            max = 50,
            message = "Company Name should have 2-50 characters long."
    )
    private String clientVendorName;


    @Schema(example = "1-xxx-xxx-xxxx")
    @Pattern(
            regexp = "^1-[0-9]{3}?-[0-9]{3}?-[0-9]{4}$",
            message = "Phone is required field and may be in any valid phone number format."
    )
    private String phone;


    @Schema(example = "www.example.com")
    @Pattern(
            regexp = "^(https?://)?([a-zA-Z0-9\\-]+\\.)+[a-zA-Z]{2,5}(/[a-zA-Z0-9\\-._~:/?#\\@!$&'()*+,;=]*)?$",
            message = "Website should have a valid format."
    )
    private String website;

    @Schema(example = "CLIENT/VENDOR")
    @NotNull(message = "Please select type")
    private ClientVendorType clientVendorType;

    @NotNull
    @Valid
    private AddressDto address;

    @Schema(hidden = true)
    private CompanyDto company;

    @Schema(hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean hasInvoice = false;


}

/*
 * imported from https://www.baeldung.com/java-regex-validate-phone-numbers :
 * @Pattern(
 *             // +111 (202) 555-0125 // +1 (202) 555-0125   // +111 123 456 789
 *             regexp = """
 *                     ^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$
 *                     "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$
 *                     "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$
 *                     """,
 *             message = "Phone is required field and may be in any valid phone number format."
 *     )
 */