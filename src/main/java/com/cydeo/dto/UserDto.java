package com.cydeo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
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

        @NotBlank(message = "Phone Number is required field.")
//    @Pattern(regexp = "^(\\+\\d{1,3}[-. ]?)?(\\d{3}[-. ]?){3}\\d{2}$\n")
        private String phone;

        private boolean enabled;
        private RoleDto role;
        private CompanyDto company;
        boolean isOnlyAdmin    ;     //(should be true if this user is only admin of any company.)
}