package com.cydeo.service;


import com.cydeo.dto.UserDto;
import jakarta.ws.rs.core.Response;

import java.util.Optional;


public interface KeycloakService {

    Response userCreate(UserDto userDto);
    void delete(String username);
    void deleteAllOrphanedUsers();

}
