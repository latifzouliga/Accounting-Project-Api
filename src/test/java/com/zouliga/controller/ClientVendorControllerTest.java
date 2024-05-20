package com.zouliga.controller;

import com.zouliga.dto.AddressDto;
import com.zouliga.dto.ClientVendorDto;
import com.zouliga.dto.CompanyDto;
import com.zouliga.entity.Address;
import com.zouliga.entity.ClientVendor;
import com.zouliga.entity.Company;
import com.zouliga.enums.ClientVendorType;
import com.zouliga.enums.CompanyStatus;
import org.junit.jupiter.api.Test;

class ClientVendorControllerTest {

    @Test
    void listAllClientsAndVendors() {
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

        ClientVendorDto clientVendorDto = ClientVendorDto.builder()
                .clientVendorName("Green tech")
                .phone("1-123-456-7890")
                .website("www.example.com")
                .clientVendorType(ClientVendorType.CLIENT)
                .address(addressDto)
                .company(companyDto)
                .hasInvoice(false)
                .build();



    }

    @Test
    void listAllClients() {
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
}