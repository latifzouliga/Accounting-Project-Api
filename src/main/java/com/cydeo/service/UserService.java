package com.cydeo.service;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;

public interface UserService {

    UserDto findByUsername(String username);

    User getLoggedInUser();
    UserDto save(UserDto userDto);

}
