package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.RoleDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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

@Tag("User service")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @Spy
    private MapperUtil mapperUtil = new MapperUtil(new ModelMapper());

    /**
     * Test Find By Username: {@link UserServiceImpl#findByUsername(String)}
     */
    @DisplayName("Find user by username")
    @Test
    void findByUsername() {

        String username = "testuser";
        User user = new User();
                user.setId(1L);
                user.setFirstname("mike");
                user.setLastname("can");
                user.setUsername(username);
                user.setPassword("Abc1");

        UserDto expectedDto = UserDto.builder()
                .id(1L)
                .firstname("mike")
                .lastname("can")
                .username(username)
                .password("Abc1")
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        UserDto actualDto = userService.findByUsername(username);
        verify(userRepository).findByUsername(username);
        assertEquals(expectedDto, actualDto);

    }

    // TODO:

    /**
     * Test List All Users By Company: {@link UserServiceImpl#listAllUsersByCompany(String, int, int)}
     */
    @Test
    void listAllUsersByCompany() {
    }

    /**
     * Test List All users By Company: {@link UserServiceImpl#listAllUsersByCompany(String)}
     */
    @Test
    void testListAllUsersByCompany() {

    }

    @Test
    void listAllAdmins() {
    }

    @Test
    void listAllFilteredUsers() {
    }
}