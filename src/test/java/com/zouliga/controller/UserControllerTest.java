package com.zouliga.controller;

import com.zouliga.dto.UserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static com.zouliga.controller.Utility.getAdminToken;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {


    @Autowired
    MockMvc mockMvc;

    private final static String AUTHORIZATION = "Authorization";
    static String bearerToken;


    @BeforeAll
    static void setUp() {
        bearerToken = "Bearer " + getAdminToken();
    }


    /**
     * Method under test: {@link UserController#create(UserDto)}
     */
    @DisplayName("Create User By Username")
    @Test
    void create() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, bearerToken)
                        .content("""
                                {
                                  "username": "email@email.com",
                                  "firstname": "hdfsf",
                                  "lastname": "sfdfd",
                                  "phone": "+1 (356) 258-3544",
                                  "password": "Abc1",
                                  "enabled": true,
                                  "role": {
                                    "id": 3,
                                    "description":"Manager"
                                  }
                                }""")
                )
                .andExpect(status().isCreated());
    }

    /**
     * {@link UserController#getAllFilteredUsers(int, int)}
     */
    @DisplayName("List All Users")
    @Test
    void listAllUsers_json() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", bearerToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Users fetched successfully"))
                .andExpect(jsonPath("data[0].username").exists())
                .andExpect(jsonPath("$.data[0].username").value("admin@bluetech.com"));
    }

    @Test
    void listAllUsers_xml() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/list")
                        .contentType(MediaType.APPLICATION_XML)
                        .accept(MediaType.APPLICATION_XML)
                        .header(AUTHORIZATION, bearerToken))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * {@link UserController#getUser(String)}
     */
    @DisplayName("Get User By Username")
    @Test
    void getUserByUsername() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/admin@bluetech.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, bearerToken)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "success": true,
                            "message": "User retrieved successfully",
                            "data": {
                                "id": 6,
                                "username": "admin@bluetech.com",
                                "firstname": "Chris",
                                "lastname": "Brown",
                                "phone": "+1 (356) 258-3544",
                                "enabled": true,
                                "role": {
                                    "id": 2,
                                    "description": "Admin"
                                },
                                "company": {
                                    "id": 3,
                                    "title": "Blue Tech",
                                    "phone": "+1 (215) 654-5268",
                                    "website": "https://www.bluetech.com",
                                    "companyStatus": "ACTIVE"
                                },
                                "isOnlyAdmin": false,
                                "onlyAdmin": false
                            }}""")
                );

    }

    /**
     * {@link UserController#updateUser(UserDto)}
     */
    @DisplayName("Update User")
    @Test
    void updateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, bearerToken)
                        .content(""" 
                                  {
                                  "id": 9,
                                  "username": "admin2@bluetech.com",
                                  "firstname": "zouliga latif",
                                  "lastname": "Brown latif",
                                  "password": "Abc1",
                                  "phone": "+1 (356) 258-3544",
                                  "enabled": true,
                                  "role": {
                                      "id": 2,
                                      "description": "Admin"
                                  }
                                }""")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstname").value("zouliga latif"))
                .andExpect(jsonPath("$.data.lastname").value("Brown latif"));
    }


    /**
     * {@link UserController#patchUser(String, Map)}
     */
    @DisplayName("Patch User")
    @Test
    void updateUserByUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/update/admin2@bluetech.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, bearerToken)
                        .content("""
                                {"firstname": "zouliga latif" }""")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstname").value("zouliga latif"));
    }


    /**
     * {@link UserController#deleteUser(String)}
     */
    @DisplayName("Delete User By Username")
    @Test
    void deleteUserByUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/delete/manager@bluetech.com")
                        .header(AUTHORIZATION, bearerToken)
                )
                .andExpect(status().isNoContent());
    }


}





















