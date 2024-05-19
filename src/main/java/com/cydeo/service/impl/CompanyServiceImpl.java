package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.exception.ResourceNotFoundException;
import com.cydeo.exception.ServiceException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.AddressRepository;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.AddressService;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;


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
        Company company = companyRepository.findByTitle(companyTitle)
                .orElseThrow(() -> new ResourceNotFoundException(companyTitle));
        return mapperUtil.convert(company, new CompanyDto());
    }

    @Override
    public CompanyDto create(CompanyDto companyDto) {
        companyDto.setCompanyStatus(CompanyStatus.PASSIVE);
        Company company = mapperUtil.convert(companyDto, new Company());
        companyRepository.save(company);
        return companyDto;
    }

    @Override
    public CompanyDto update(CompanyDto updatedCompany) {
        Company existingCompany = companyRepository.findByTitle(updatedCompany.getTitle())
                .orElseThrow(() -> new ResourceNotFoundException(updatedCompany.getTitle()));

        Long existingAddressId = existingCompany.getAddress().getId();

        updatedCompany.getAddress().setId(existingAddressId);
        updatedCompany.setId(existingCompany.getId());
        companyRepository.save(mapperUtil.convert(updatedCompany, new Company()));
        return updatedCompany;
    }

    @Override
    public CompanyDto activateDeactivate(Long companyId, String companyStatus) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(companyId)));

        switch (companyStatus.toLowerCase().trim()) {
            case "active" -> company.setCompanyStatus(CompanyStatus.ACTIVE);
            case "passive" -> company.setCompanyStatus(CompanyStatus.PASSIVE);
            default -> throw new ServiceException("Wrong company companyStatus. Please use Active/Passive: " + companyStatus);
        }

        companyRepository.save(company);
        return mapperUtil.convert(company, new CompanyDto());
    }

    @Override
    public CompanyDto findCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));
        return mapperUtil.convert(company, new CompanyDto());
    }

}

















