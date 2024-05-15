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

    @Schema(hidden = true)
    private Long id;

    @Schema(example = "user@email.com")
    @NotBlank(message = "Username is required field.")
    @Size(min = 2, max = 50)
    private String username;   // must be unique

    @Schema(example = "Abc1")
    @NotBlank(message = "Password is required field.")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(example = "Latif")
    @NotBlank(message = "First Name is required field.")
    @Size(min = 2, max = 50)
    private String firstname;

    @Schema(example = "Zouliga")
    @NotBlank(message = "Last Name is required field.")
    @Size(min = 2, max = 50)
    private String lastname;


    @Schema(example = "1-xxx-xxx-xxxx")
    @Pattern(
            regexp = "^1-[0-9]{3}?-[0-9]{3}?-[0-9]{4}$",
            message = "Phone is required field and may be in any valid phone number format."
    )
    private String phone;

    private boolean enabled;

    @Schema(description = "Role ID", implementation = RoleIdOnly.class)
    private RoleDto role;

    @Schema(description = "Company ID", implementation = CompanyIdOnly.class)
    private CompanyDto company;

//    @Schema(hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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