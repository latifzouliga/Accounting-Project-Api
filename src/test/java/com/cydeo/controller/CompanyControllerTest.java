package com.cydeo.controller;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.Address;
import com.cydeo.entity.Company;
import com.cydeo.enums.CompanyStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.cydeo.controller.Utils.getRootToken;
import static com.cydeo.controller.Utils.toJsonString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest {


    @Autowired
    MockMvc mockMvc;
    private final String AUTHORIZATION = "Authorization";
    static String bearerToken;

    @BeforeAll
    static void setUp() {
        bearerToken = "Bearer ".concat(getRootToken());
    }


    @Test
    void listCompanies() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/companies/list")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, bearerToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Successfully retrieved companies data"))
                .andExpect(jsonPath("data[0].title").isNotEmpty())
                .andExpect(jsonPath("data[0].title").value("Green Tech"))
                .andExpect(jsonPath("data[1].address.addressLine1").value("West Street"));
    }

    @Test
    void getCompanyById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/companies/list/1")
                        .accept(MediaType.APPLICATION_XML)
                        .header(AUTHORIZATION, bearerToken)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void createCompany() throws Exception {

        Address address = new Address();
        address.setId(3L);
        address.setAddressLine1("1232c12 main st");
        address.setAddressLine2("1232c12 main st");
        address.setCity("Pittsburgh");
        address.setState("Pa");
        address.setCountry("USA");
        address.setZipCode("12312");
        Company company = new Company();
        company.setId(3L);
        company.setTitle("Green Tech co12");
        company.setPhone("1-111-111-1111");
        company.setWebsite("www.example.com");
        company.setAddress(address);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/companies/create")
                        .header(AUTHORIZATION, bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJsonString(company))
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("data.title").exists());
    }

    @Test
    void updateCompany() throws Exception {
        Address address = new Address();
        address.setId(3L);
        address.setAddressLine1("1232c12 main st");
        address.setAddressLine2("1232c12 main st");
        address.setCity("Pittsburgh");
        address.setState("Pa");
        address.setCountry("USA");
        address.setZipCode("12312");
        Company company = new Company();
        company.setId(3L);
        company.setTitle("Green Tech co12");
        company.setPhone("1-111-111-1111");
        company.setWebsite("www.example.com");
        company.setAddress(address);


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/companies/update/3")
                        .header(AUTHORIZATION, bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJsonString(company))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.title").exists());
    }


    @Test
    void activateCompany() throws Exception {
        Address address = new Address();
        address.setId(3L);
        address.setAddressLine1("1232c12 main st");
        address.setAddressLine2("1232c12 main st");
        address.setCity("Pittsburgh");
        address.setState("Pa");
        address.setCountry("USA");
        address.setZipCode("12312");
        Company company = new Company();
        company.setId(3L);
        company.setTitle("Green Tech co12");
        company.setPhone("1-111-111-1111");
        company.setWebsite("www.example.com");
        company.setAddress(address);
        company.setCompanyStatus(CompanyStatus.PASSIVE);

        mockMvc.perform(MockMvcRequestBuilders.put("/companies/update-status/3/active")
                .header(AUTHORIZATION, bearerToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.companyStatus").value("ACTIVE"));


    }
}