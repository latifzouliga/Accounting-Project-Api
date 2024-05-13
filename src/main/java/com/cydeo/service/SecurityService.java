package com.cydeo.service;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService {

    User getLoggedInUser();
}
