package com.cydeo.dto;

import com.cydeo.enums.CompanyStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    private Long id;

    @NotBlank(message = "Title is a required field.")
    @Size(max = 100, min = 2, message = "Title should be 2-100 characters long.")
    private String title;

    @Pattern(
            // +111 123 45 67 89  // +111 (202) 555-0125 // +1 (202) 555-0125   // +111 123 456 789
            regexp =  """
            ^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$
            |^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$
            |^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$
            """,
            message = "Phone number is required field and may be in any valid phone number format."
    )
    private String phone;

    @NotBlank(message = "Website is a required field.")
    @Pattern(
            regexp = "^http(s{0,1})://[a-zA-Z0-9/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*",
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
