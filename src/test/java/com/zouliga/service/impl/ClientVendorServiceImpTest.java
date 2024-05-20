package com.zouliga.service.impl;

import com.zouliga.dto.ClientVendorDto;
import com.zouliga.entity.ClientVendor;
import com.zouliga.entity.Company;
import com.zouliga.entity.User;
import com.zouliga.enums.ClientVendorType;
import com.zouliga.mapper.MapperUtil;
import com.zouliga.repository.ClientVendorRepository;
import com.zouliga.service.SecurityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ClientVendorServiceImpTest {


    @Mock
    private ClientVendorRepository clientVendorRepository;
    @Mock
    private SecurityService securityService;
    @InjectMocks
    private ClientVendorServiceImp clientVendorService;
    @Spy
    private MapperUtil mapperUtil = new MapperUtil(new ModelMapper());


    @Test
    void listAllClientVendors() {
        Sort sort = Sort.by("clientVendorType").and(Sort.by("clientVendorName"));
        PageRequest pageRequest = PageRequest.of(0, 10, sort);


        User loggedInUser = getGetLoggedInUser();
        String companyTitle = loggedInUser.getCompany().getTitle();
        List<ClientVendorDto> expectedClientVendorList = getClientVendorDtoList();
        List<ClientVendor> clientVendorList = getClientVendorList();


        when(securityService.getLoggedInUser()).thenReturn(loggedInUser);
        when(clientVendorRepository.findAllByCompany_Title(companyTitle, pageRequest)).thenReturn(clientVendorList);

        List<ClientVendorDto> returnedResult = clientVendorService.listAllClientVendors(0, 10);

        verify(clientVendorRepository).findAllByCompany_Title(companyTitle, pageRequest);
        Assertions.assertEquals(expectedClientVendorList, returnedResult);

    }

    private static User getGetLoggedInUser() {
        Company company = new Company();
        company.setTitle("Company 1");
        User user = new User();
        user.setUsername("mike");
        user.setPassword("Abc1");
        user.setCompany(company);
        return user;
    }


    @Test
    void listAllByClientVendorType() {
        PageRequest pageRequest = PageRequest.of(0, 10)
                .withSort(Sort.Direction.ASC, "clientVendorName");

        List<ClientVendorDto> expectedClientVendorList = getClientVendorDtoList();
        List<ClientVendor> clientVendorList = getClientVendorList();
        User loggeInUser = getGetLoggedInUser();
        String companyTitle = loggeInUser.getCompany().getTitle();


        when(securityService.getLoggedInUser()).thenReturn(loggeInUser);
        when(clientVendorRepository.findAllByCompany_TitleAndClientVendorType(
                companyTitle,
                ClientVendorType.CLIENT,
                pageRequest
        )).thenReturn(clientVendorList);

        List<ClientVendorDto> returnedClientVenderList = clientVendorService.listAllByClientVendorType(
                ClientVendorType.CLIENT.name(), 0, 10
        );

        verify(clientVendorRepository).findAllByCompany_TitleAndClientVendorType(
                companyTitle,
                ClientVendorType.CLIENT,
                pageRequest);
        Assertions.assertEquals(expectedClientVendorList, returnedClientVenderList);
        Assertions.assertEquals(returnedClientVenderList.get(0).getClientVendorType(),ClientVendorType.CLIENT);

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

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void patch() {
    }

    @Test
    void delete() {
    }

    @Test
    void findById() {
    }
}