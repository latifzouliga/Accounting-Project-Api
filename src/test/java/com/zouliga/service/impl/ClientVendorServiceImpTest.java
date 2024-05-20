package com.zouliga.service.impl;

import com.zouliga.dto.AddressDto;
import com.zouliga.dto.ClientVendorDto;
import com.zouliga.dto.CompanyDto;
import com.zouliga.entity.Address;
import com.zouliga.entity.ClientVendor;
import com.zouliga.entity.Company;
import com.zouliga.entity.User;
import com.zouliga.enums.ClientVendorType;
import com.zouliga.enums.CompanyStatus;
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

import static org.mockito.Mockito.*;


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
        Assertions.assertEquals(returnedClientVenderList.get(0).getClientVendorType(), ClientVendorType.CLIENT);

    }

    @Test
    void create() {

        User loggedInUser = getGetLoggedInUser();

        ClientVendorDto clientVendorDto = getClientVendorDto();
        ClientVendor clientVendor = getClientVendor();


        when(securityService.getLoggedInUser()).thenReturn(loggedInUser);
        doReturn(clientVendor).when(mapperUtil).convert(any(ClientVendorDto.class), any(ClientVendor.class));
        when(clientVendorRepository.save(clientVendor)).thenReturn(clientVendor);
        doReturn(clientVendorDto).when(mapperUtil).convert(any(ClientVendor.class), any(ClientVendorDto.class));

        ClientVendorDto returnedResult = clientVendorService.create(clientVendorDto);

        verify(clientVendorRepository).save(clientVendor);
        Assertions.assertEquals(clientVendorDto, returnedResult);


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






    /**
     * Test Data: loggedInUser, clientVendorDto, clientVendor, clientVendorDtoList, clientVendorList
     *
     * @return
     */
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


    private static ClientVendorDto getClientVendorDto() {
        AddressDto addressDto = AddressDto.builder()
                .addressLine1("123 main st")
                .addressLine2("Suite 100")
                .city("Pittsburgh")
                .state("PA")
                .country("USA")
                .zipCode("15555")
                .build();

        CompanyDto companyDto = CompanyDto.builder()
                .title("Green tech")
                .phone("1-123-456-7890")
                .website("www.example.com")
                .address(addressDto)
                .companyStatus(CompanyStatus.ACTIVE)
                .build();

        return ClientVendorDto.builder()
                .clientVendorName("Green tech")
                .phone("1-123-456-7890")
                .website("www.example.com")
                .clientVendorType(ClientVendorType.CLIENT)
                .address(addressDto)
                .company(companyDto)
                .hasInvoice(false)
                .build();
    }

    private static ClientVendor getClientVendor() {
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
        company.setAddress(address);
        company.setCompanyStatus(CompanyStatus.ACTIVE);


        ClientVendor clientVendor = new ClientVendor();
        clientVendor.setClientVendorName("Green tech");
        clientVendor.setPhone("1-123-456-7890");
        clientVendor.setWebsite("www.example.com");
        clientVendor.setClientVendorType(ClientVendorType.CLIENT);
        clientVendor.setAddress(address);
        clientVendor.setCompany(company);

        return clientVendor;
    }


}