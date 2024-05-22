package com.zouliga.controller;

import com.zouliga.dto.AddressDto;
import com.zouliga.dto.ClientVendorDto;
import com.zouliga.dto.CompanyDto;
import com.zouliga.entity.Address;
import com.zouliga.entity.ClientVendor;
import com.zouliga.entity.Company;
import com.zouliga.entity.User;
import com.zouliga.enums.ClientVendorType;
import com.zouliga.enums.CompanyStatus;
import com.zouliga.service.KeycloakService;
import com.zouliga.service.SecurityService;
import com.zouliga.service.impl.ClientVendorServiceImp;
import com.zouliga.service.impl.KeycloakServiceImpl;
import com.zouliga.service.impl.SecurityServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.zouliga.controller.Utils.getAdminToken;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest
class ClientVendorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ClientVendorServiceImp clientVendorService;
    @MockBean
    private SecurityServiceImpl securityService;
    @MockBean
    private KeycloakServiceImpl keycloakService;
    @MockBean
    private CategoryController categoryController;
    @MockBean
    CompanyController companyController;
    @MockBean
    private ProductController productController;
    @MockBean
    private UserController userController;

    private static String bearerToken;

    @BeforeAll
    static void setUp() {
        bearerToken = "Bearer " + getAdminToken();
    }

    @Test
    void listAllClientsAndVendors() throws Exception {
        when(securityService.getLoggedInUser()).thenReturn(getGetLoggedInUser());
        when(clientVendorService.listAllClientVendors(0, 10)).thenReturn(getClientVendorDtoList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/client-vendor/list")
                        .header("Authorization", bearerToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void listAllClients() throws Exception {
        when(clientVendorService.listAllByClientVendorType("client", 0, 10))
                .thenReturn(getClientVendorDtoList());

        mockMvc.perform(MockMvcRequestBuilders.get("/client-vendor/list/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", bearerToken)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createClientVendor() {
    }

    @Test
    void updateClientVendor() {
    }

    @Test
    void patchClientVendor() {
    }

    @Test
    void deleteClientVendor() {
    }

    private static User getGetLoggedInUser() {
        Address address = new Address();
        address.setAddressLine1("123 main st");
        address.setAddressLine2("Suite 100");
        address.setCity("Pittsburgh");
        address.setState("PA");
        address.setCountry("USA");
        address.setZipCode("15555");

        Company company = new Company();
        company.setTitle("Green tech");
        company.setPhone("1-123-456-7890");
        company.setWebsite("www.example.com");
        company.setCompanyStatus(CompanyStatus.ACTIVE);
        company.setAddress(address);


        User user = new User();
        user.setUsername("mike");
        user.setPassword("Abc1");
        user.setCompany(company);
        return user;
    }

    private static List<ClientVendor> getClientVendorList() {
        ClientVendor clientVendor1 = new ClientVendor();
        clientVendor1.setClientVendorName("Client company 1");
        clientVendor1.setClientVendorType(ClientVendorType.CLIENT);
        ClientVendor clientVendor2 = new ClientVendor();
        clientVendor2.setClientVendorName("Client company 2");
        clientVendor2.setClientVendorType(ClientVendorType.CLIENT);
        ClientVendor clientVendor3 = new ClientVendor();
        clientVendor3.setClientVendorType(ClientVendorType.CLIENT);
        clientVendor3.setClientVendorName("Client company 3");
        return List.of(clientVendor1, clientVendor2, clientVendor3);
    }

    private static List<ClientVendorDto> getClientVendorDtoList() {
        return List.of(
                ClientVendorDto.builder()
                        .clientVendorName("Client company 1")
                        .clientVendorType(ClientVendorType.CLIENT)
                        .build(),
                ClientVendorDto.builder()
                        .clientVendorName("Client company 2")
                        .clientVendorType(ClientVendorType.CLIENT)
                        .build(),
                ClientVendorDto.builder()
                        .clientVendorName("Client company 3")
                        .clientVendorType(ClientVendorType.CLIENT)
                        .build()
        );
    }
}