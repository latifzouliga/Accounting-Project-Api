package com.cydeo.controller;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PreAuthorize("(hasRole('Admin'))")
    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> create(@RequestBody UserDto userDto) {
        UserDto user = userService.save(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResponseWrapper.builder()
                        .code(HttpStatus.CREATED.value())
                        .success(true)
                        .message("User created successfully")
                        .data(user)
                        .build()
                );
    }

}