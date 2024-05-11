package com.cydeo.controller;

import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.RoleDto;
import com.cydeo.dto.UserDto;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.cydeo.controller.Utility.toJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {


    @Autowired
    MockMvc mvc;

    @InjectMocks
    UserServiceImpl userService;
    @Spy
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Spy
    MapperUtil mapperUtil = new MapperUtil(new ModelMapper());


    static CompanyDto company;

    static UserDto userDto;

    static String bearerToken;

    @BeforeAll
    static void setUp() {
        RoleDto role = RoleDto.builder().description("The characteristics of someone or something").id(1L).build();

        company = CompanyDto.builder()
                .companyStatus(CompanyStatus.ACTIVE)
                .id(1L)
                .phone("6625550144")
                .title("Dr")
                .website("Website")
                .build();

        userDto = UserDto.builder()
                .company(company)
                .enabled(true)
                .firstname("Jane")
                .username("janedoe")
                .id(1L)
                .lastname("Doe")
                .password("iloveyou")
                .phone("6625550144")
                .role(role)
                .build();

        bearerToken = "Bearer " + Utility.getAdminToken();

    }

    /**
     * Method under test: {@link UserController#create(UserDto)}
     */
    @Test
    void create() throws Exception {
        when(userService.save(userDto)).thenReturn(userDto);
        mvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(userDto))
                        .header("Authorization", bearerToken))
                .andExpect(status().isCreated());
    }
}





















