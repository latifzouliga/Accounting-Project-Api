package com.cydeo.service.impl;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.exception.UserNotFoundException;
import com.cydeo.mapper.MapperUtil;
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
    private final MapperUtil mapper;
    private final PasswordEncoder passwordEncoder;
    private final KeycloakService keycloakService;


    public UserServiceImpl(UserRepository userRepository,
                           MapperUtil mapper,
                           PasswordEncoder passwordEncoder,
                           KeycloakService keycloakService) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.keycloakService = keycloakService;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return mapper.convert(user, new UserDto());
    }

    @Override
    public User getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public UserDto save(UserDto userDto) {
        String password = userDto.getPassword();
        User user = mapper.convert(userDto, new User());
        if (password != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(password));
            user.setInsertDateTime(LocalDateTime.now());
            user.setLastUpdateDateTime(LocalDateTime.now());
            user.setLastUpdateUserId(1L);
            user.setInsertUserId(1L);
            userRepository.save(user);
            keycloakService.userCreate(userDto);
            return userDto;
        } else {
            throw new RuntimeException("Password can not be null");
        }

    }


}











