package com.cydeo.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long id;
    @NotBlank(message = "Username is required field.")
    @Size(min = 2, max = 50)
    private String username;   // must be unique

    @NotBlank(message = "Password is required field.")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "First Name is required field.")
    @Size(min = 2, max = 50)
    private String firstname;

    @NotBlank(message = "Last Name is required field.")
    @Size(min = 2, max = 50)
    private String lastname;


//        @Pattern(regexp = "^\\+?\\d{1,3}\\s?\\(?\\d{3}\\)?\\s?\\d{3}-\\d{4}$\n",
//                message = "Phone is required field and may be in any valid phone number format.")
//    @Pattern(
//            // +111 (202) 555-0125 // +1 (202) 555-0125   // +111 123 456 789
//            regexp = " ^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$ |^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$ |^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$",
//            message = "Phone is required field and may be in any valid phone number format."
//    )
    private String phone;

    private boolean enabled;

    @Schema(description = "Role ID", implementation = RoleIdOnly.class)
    private RoleDto role;

    @Schema(description = "Company ID", implementation = CompanyIdOnly.class)
    private CompanyDto company;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(hidden = true)
    boolean isOnlyAdmin;     //(should be true if this user is only admin of any company.)


    @Getter
    static class RoleIdOnly {
        @Schema(description = "Role ID")
        private Long id;
    }

    @Getter
    static class CompanyIdOnly {
        @Schema(description = "Company ID")
        private Long id;
    }
}