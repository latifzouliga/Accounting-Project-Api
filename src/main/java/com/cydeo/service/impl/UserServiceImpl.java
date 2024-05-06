package com.cydeo.service.impl;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.MapperUtil;
import com.cydeo.entity.User;
import com.cydeo.exception.UserNotFoundException;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final MapperUtil mapperUtil;
    private final KeycloakService keycloakService;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, MapperUtil mapperUtil, KeycloakService keycloakService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.mapperUtil = mapperUtil;
        this.keycloakService = keycloakService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException(username));
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = mapperUtil.convert(userDto, new User());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setInsertDateTime(LocalDateTime.now());
        user.setLastUpdateDateTime(LocalDateTime.now());
        //hardcoding user Ids
        user.setLastUpdateUserId(1L);
        user.setInsertUserId(1L);
        System.out.println(user);
        System.out.println(userDto);
        userRepository.save(user);
        keycloakService.userCreate(userDto);

        return userDto;
    }


}











