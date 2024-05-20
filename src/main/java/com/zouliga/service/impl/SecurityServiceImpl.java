package com.zouliga.service.impl;

import com.zouliga.entity.User;
import com.zouliga.exception.ResourceNotFoundException;
import com.zouliga.repository.UserRepository;
import com.zouliga.service.SecurityService;
import com.zouliga.service.UserService;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class SecurityServiceImpl implements SecurityService {


    public final UserService userService;
    private final UserRepository userRepository;

    public SecurityServiceImpl(@Lazy UserService userService, @Lazy UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public User getLoggedInUser() {
        String username =  SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User not found"));

    }



}
