package com.cydeo.service.impl;

import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.AddressRepository;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.AddressService;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private  CompanyRepository companyRepository;

    @Mock
    private  MapperUtil mapperUtil;
    @Mock
    private  SecurityService securityService;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void getCompanyDtoByLoggedInUser() {
    }

    @Test
    void listAllCompanies() {

    }

    @Test
    void findCompanyByCompanyTitle() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void activateDeactivate() {
    }

    @Test
    void findCompanyById() {
    }
}