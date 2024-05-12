package com.cydeo.controller;

import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.RoleDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Company;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.impl.KeycloakServiceImpl;
import com.cydeo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    @Mock
    static UserRepository userRepository;
    @Mock
    static KeycloakService keycloakService;
    @Mock
    static PasswordEncoder passwordEncoder;
    @Spy
    static MapperUtil mapperUtil = new MapperUtil(new ModelMapper());


    static CompanyDto company;

    @Spy
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
                .password("Iloveyou1")
                .phone("6625550144")
                .role(role)
                .build();
        String admin = "admin@bluetech.com";
        String root = "root@cydeo.com";
        String password = "Abc1";

        bearerToken = "Bearer " + "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJKUVBDMG5Ic2VPMkhjY1ByMlN3UlFycDhvS0pTdHlfNFBSYVBmeFlyT28wIn0.eyJleHAiOjE3MTU0NzQ3NDgsImlhdCI6MTcxNTQ3NDQ0OCwianRpIjoiZjYwZDM2ZjctZDQxNS00MzgxLWJmNDUtNzJlY2IxMDNjNmU3IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy96b3VsaWdhLWRldiIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJhODk4NjU5ZC1lNDA1LTRjNzAtODAzMC00Mjg3MjYzZGIxYzUiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJBY2NvdW50aW5nLVByb2plY3QtQXBpIiwic2Vzc2lvbl9zdGF0ZSI6ImVlNDJhNmFkLWJhNTUtNDlhOS1iNGQ0LWUzNmY1Y2E1ZTk4NyIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiUm9vdCIsImRlZmF1bHQtcm9sZXMtem91bGlnYS1kZXYiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiQWNjb3VudGluZy1Qcm9qZWN0LUFwaSI6eyJyb2xlcyI6WyJSb290Il19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJzaWQiOiJlZTQyYTZhZC1iYTU1LTQ5YTktYjRkNC1lMzZmNWNhNWU5ODciLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJvYmVydCBNYXJ0aW4iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJyb290QGN5ZGVvLmNvbSIsImdpdmVuX25hbWUiOiJSb2JlcnQiLCJmYW1pbHlfbmFtZSI6Ik1hcnRpbiIsImVtYWlsIjoicm9vdEBjeWRlby5jb20ifQ.htI0DURHZ3l6EsXDTSxgUgDjxEwTdOAuXNjgUfLSlZH5yC1sHmDwFpMjfCPThXt-8-BtwiATUuLsxu6qbCWGAsEBBfsg9NvlZl3C5IjpTUm6R0JRqtGG2dEXwfgfe7nVXhGFwBgL63-Ns8MjtcDa1TvAXM0dmV_EnYN-esdgv-quacHqQryvDPs7hqIv4umEgBSZ9ewKtlkRVuBbA6Lqo3HZOFzEuLMn4enPHOHETaHjKWwkJ2iA3SuHLmAaDdLVnryZH8aOo6rzfEA81h6kpPed-lYII3_Tb83997ijXKOUg5uJT99WEDumF2g4wRo4xMDzWNMWf0D5bJ-cfWuGqQ";

    }

    /**
     * Method under test: {@link UserController#create(UserDto)}
     */
    @Test
    void create() throws Exception {
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
                .password("Iloveyou1")
                .phone("6625550144")
                .role(role)
                .build();



        Company company = new Company();
        company.setId(1L);
        company.setTitle("any company");

        Role role1 = new Role();
        role1.setId(2L);
        role1.setDescription("Admin");

        User user1 = new User();
        user1.setId(1L);
        user1.setFirstname("mike");
        user1.setLastname("can");
        user1.setUsername("user1@email.com");
        user1.setRole(role1);
        user1.setCompany(company);
        user1.setPassword("Abc1");

//        when(userService.save(userDto)).thenReturn(userDto);
//        when(userRepository.save(user1)).thenReturn(user1);
        mvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(userDto))
                        .header("Authorization", bearerToken))
                .andExpect(status().isCreated());
    }
}





















