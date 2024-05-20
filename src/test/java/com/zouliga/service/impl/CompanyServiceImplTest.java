package com.zouliga.service.impl;

import com.zouliga.dto.AddressDto;
import com.zouliga.dto.CompanyDto;
import com.zouliga.entity.Address;
import com.zouliga.entity.Company;
import com.zouliga.enums.CompanyStatus;
import com.zouliga.mapper.MapperUtil;
import com.zouliga.repository.CompanyRepository;
import org.junit.jupiter.api.DisplayName;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;
    @Spy
    private MapperUtil mapperUtil = new MapperUtil(new ModelMapper());
    @InjectMocks
    private CompanyServiceImpl companyService;


    /**
     * List All Companies: {@link CompanyServiceImpl#listAllCompanies(int, int)}
     */
    @DisplayName("List All Companies")
    @Test
    void listAllCompanies() {
        List<CompanyDto> expectedResult = List.of(
                CompanyDto.builder().title("Company 1").id(1L).build(),
                CompanyDto.builder().title("Company 2").id(2L).build(),
                CompanyDto.builder().title("Company 3").id(3L).build()
        );

        Company company1 = new Company();
        company1.setId(1L);
        company1.setTitle("Company 1");

        Company company2 = new Company();
        company2.setId(2L);
        company2.setTitle("Company 2");

        Company company3 = new Company();
        company3.setId(3L);
        company3.setTitle("Company 3");

        List<Company> companyList = List.of(company1, company2, company3);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("companyStatus"));
        when(companyRepository.findAllCompanies(pageRequest)).thenReturn(companyList);
        List<CompanyDto> returnedCompanyList = companyService.listAllCompanies(0, 10);
        verify(companyRepository).findAllCompanies(pageRequest);
        assertEquals(expectedResult, returnedCompanyList);
    }

    /**
     * Find Company By Company Title: {@link CompanyServiceImpl#findCompanyByCompanyTitle(String)}
     */
    @DisplayName("Find Company By Company Title")
    @Test
    void findCompanyByCompanyTitle() {
        String companyTitle = "Company 1";
        CompanyDto expectedResult = CompanyDto.builder().title(companyTitle).id(1L).build();

        Company company = new Company();
        company.setId(1L);
        company.setTitle(companyTitle);

        when(companyRepository.findByTitle(companyTitle)).thenReturn(Optional.of(company));
        CompanyDto returnedResult = companyService.findCompanyByCompanyTitle(companyTitle);
        verify(companyRepository).findByTitle(companyTitle);
        assertEquals(expectedResult, returnedResult);

    }


    /**
     * Create Company: {@link CompanyServiceImpl#create(CompanyDto)} )}
     */
    @DisplayName("Create Company")
    @Test
    void create() {

        CompanyDto companyDto = CompanyDto.builder().title("Company 1").id(1L).build();

        Company company = new Company();
        company.setId(1L);
        company.setTitle("Company 1");

        doReturn(company).when(mapperUtil).convert(any(CompanyDto.class), any(Company.class));
        when(companyRepository.save(company)).thenReturn(company);

        CompanyDto returnedResult = companyService.create(companyDto);

        assertEquals(companyDto, returnedResult);

    }

    /**
     * Update Company: {@link CompanyServiceImpl#update(Long, CompanyDto)}
     */
    @DisplayName("Update Company")
    @Test
    void update() {
        String companyTitle = "Company 1";
        AddressDto addressDto = AddressDto.builder().id(1L).build();
        CompanyDto companyDto = CompanyDto.builder().title(companyTitle).id(1L).address(addressDto).build();

        Address address = new Address();
        address.setId(1L);

        Company company = new Company();
        company.setId(1L);
        company.setTitle(companyTitle);
        company.setAddress(address);

        when(companyRepository.findById(companyDto.getId())).thenReturn(Optional.of(company));
        when(companyRepository.save(company)).thenReturn(company);
        doReturn(company).when(mapperUtil).convert(any(CompanyDto.class), any(Company.class));
        companyDto.setId(company.getId());

        CompanyDto returnedResult = companyService.update(companyDto.getId(),companyDto);

        verify(companyRepository).findById(companyDto.getId());
        verify(companyRepository).save(company);
        assertEquals(companyDto, returnedResult);

    }

    /**
     * Activate/Deactivate Company: {@link CompanyServiceImpl#activateDeactivate(Long, String)}
     */
    @DisplayName("Activate/Deactivate Company")
    @Test
    void activateDeactivate() {
        String companyTitle = "Company 1";

        Address address = new Address();
        address.setId(1L);

        Company company = new Company();
        company.setId(1L);
        company.setTitle(companyTitle);
        company.setAddress(address);

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        CompanyDto returnedResult = companyService.activateDeactivate(1L, "Active ");

        assertEquals(returnedResult.getCompanyStatus(), CompanyStatus.ACTIVE);
        assertNotEquals(returnedResult.getCompanyStatus(),CompanyStatus.PASSIVE);

    }

    /**
     * Find Company By Id: {@link CompanyServiceImpl#findCompanyById(Long)}
     */
    @DisplayName("Find Company By Id")
    @Test
    void findCompanyById() {
        String companyTitle = "Company 1";

        CompanyDto companyDto = CompanyDto.builder().title(companyTitle).id(1L).build();

        Company company = new Company();
        company.setId(1L);
        company.setTitle(companyTitle);

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        doReturn(companyDto).when(mapperUtil).convert(any(Company.class),any(CompanyDto.class));

        CompanyDto returnedResult = companyService.findCompanyById(1L);

        verify(companyRepository).findById(1L);
        assertEquals(companyDto,returnedResult);

    }
}