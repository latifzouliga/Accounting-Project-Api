package com.zouliga.service.impl;

import com.zouliga.dto.CompanyDto;
import com.zouliga.entity.Company;
import com.zouliga.enums.CompanyStatus;
import com.zouliga.exception.ResourceNotFoundException;
import com.zouliga.exception.ServiceException;
import com.zouliga.mapper.MapperUtil;
import com.zouliga.repository.CompanyRepository;
import com.zouliga.service.CompanyService;
import com.zouliga.service.SecurityService;
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
    public CompanyDto update(Long id, CompanyDto updatedCompany) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));

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

















