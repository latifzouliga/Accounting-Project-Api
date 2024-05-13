package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Address;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.exception.ServiceException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.AddressRepository;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.AddressService;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.keycloak.jose.jwk.JWK;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final AddressService addressService;
    private final AddressRepository addressRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    @Override
    public CompanyDto getCompanyDtoByLoggedInUser() {
        return null;
    }

    @Override
    public List<CompanyDto> listAllCompanies(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("companyStatus"));
        List<Company> companyPage = companyRepository.findAllCompanies(pageRequest);
       return companyPage.stream()
               .map(company -> mapperUtil.convert(company, new CompanyDto()))
               .collect(Collectors.toList());
    }

    @Override
    public CompanyDto findCompanyByCompanyTitle(String companyTitle) {
        Company company = companyRepository.findAllByTitle(companyTitle)
                .orElseThrow(() -> new ServiceException(companyTitle));
        return mapperUtil.convert(company,new CompanyDto());
    }

    @Override
    public CompanyDto create(CompanyDto companyDto) {
        companyDto.setCompanyStatus(CompanyStatus.PASSIVE);
        Company company = mapperUtil.convert(companyDto, new Company());
        // TODO:
//        company.setInsertUserId(getLoggeDIndUser().getId());
//        company.setLastUpdateUserId(getLoggeDIndUser().getId());

        Address address = company.getAddress();
//        address.setInsertUserId(getLoggeDIndUser().getId());
//        address.setLastUpdateUserId(getLoggeDIndUser().getId());

        addressRepository.save(address);

        companyRepository.save(company);
        return companyDto;
    }

    private User getLoggeDIndUser(){
        return securityService.getLoggedInUser();
    }
}

















