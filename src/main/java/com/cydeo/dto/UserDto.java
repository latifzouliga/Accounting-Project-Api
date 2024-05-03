package com.cydeo.dto;

import lombok.Builder;

@Builder
public record UserDto (
        Long id,
        String username,
        String password,
        String confirmPassword,
        String firstname,
        String lastname,
        String phone,
        RoleDto role,
        CompanyDto company,
        boolean isOnlyAdmin         //(should be true if this user is only admin of any company.)
){
}
