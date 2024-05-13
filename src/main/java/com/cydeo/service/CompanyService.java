package com.cydeo.service;

import com.cydeo.dto.CompanyDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {

    CompanyDto getCompanyDtoByLoggedInUser();

    List<CompanyDto> listAllCompanies(int pageNo, int pageSize);

    CompanyDto findCompanyByCompanyTitle(String companyTitle);

    CompanyDto create(CompanyDto companyDto);

    CompanyDto update(CompanyDto companyDto);

    CompanyDto activateDeactivate(Long companyId, String status);
}
