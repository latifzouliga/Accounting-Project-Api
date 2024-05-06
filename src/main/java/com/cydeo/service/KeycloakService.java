package com.cydeo.service;


import com.cydeo.dto.UserDto;
import jakarta.ws.rs.core.Response;


public interface KeycloakService {

    Response userCreate(UserDto userDto);
    void delete(String username);
    UserDto getLoggedInUser();
}
