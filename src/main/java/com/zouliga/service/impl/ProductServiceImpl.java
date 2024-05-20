package com.zouliga.service.impl;

import com.zouliga.dto.ProductDto;
import com.zouliga.entity.User;
import com.zouliga.mapper.MapperUtil;
import com.zouliga.repository.ProductRepository;
import com.zouliga.service.ProductService;
import com.zouliga.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;


    @Override
    public List<ProductDto> listAllProducts(int pageNo, int pageSize) {
        String companyTitle = getCurrentUser().getCompany().getTitle();
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("category").and(Sort.by("name")));
        return productRepository.findAllByCompanyTitle(companyTitle,pageable)
                .stream()
                .map(product -> mapperUtil.convert(product, new ProductDto()))
                .collect(Collectors.toList());
    }

    private User getCurrentUser(){
       return securityService.getLoggedInUser();
    }
}














