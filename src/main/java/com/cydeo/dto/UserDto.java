package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
        private Long id;
        private String username;   // must be unique
        private String password;
        private String firstname;
        private String lastname;
        private String phone;
        private boolean enabled;
        private RoleDto role;
        private CompanyDto company;
        boolean isOnlyAdmin    ;     //(should be true if this user is only admin of any company.)
}