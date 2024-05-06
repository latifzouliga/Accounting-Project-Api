package com.cydeo.service.impl;

import com.cydeo.dto.RoleDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void findByUsername() {

        String username = "testuser";
        User user = new User();
        UserDto expectedDto = UserDto.builder()
//                .id(1L)
//                .username("username")
//                .lastname("lastname")
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDto.class)).thenReturn(expectedDto);
        UserDto actualDto = userService.findByUsername(username);
        assertEquals(expectedDto, actualDto);

    }

}