package com.zouliga.service;


import com.zouliga.dto.UserDto;
import jakarta.ws.rs.core.Response;


public interface KeycloakService {

    Response userCreate(UserDto userDto);
    void delete(String username);
    void deleteAllOrphanedUsers();

}
