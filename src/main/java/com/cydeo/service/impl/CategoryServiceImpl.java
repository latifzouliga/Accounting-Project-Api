package com.cydeo.service.impl;

import com.cydeo.dto.CategoryDto;
import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Category;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.exception.ResourceNotFoundException;
import com.cydeo.exception.ServiceException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.service.CategoryService;
import com.cydeo.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;

    @Override
    public List<CategoryDto> listAllCategories() {
        String companyTitle = getLoggedInUser().getCompany().getTitle();
        List<Category> categoryList = categoryRepository.findAllByCompany_Title(
                companyTitle, Sort.by("description")
        );
        return categoryList
                .stream()
                .map(category -> {
                    CategoryDto categoryDto = mapperUtil.convert(category, new CategoryDto());
                    categoryDto.setHasProduct(checkIfHasProducts(categoryDto));
                    return categoryDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        categoryDto.setCompany(mapperUtil.convert(getLoggedInUser().getCompany(), new CompanyDto()));
        Category category = mapperUtil.convert(categoryDto, new Category());
        categoryRepository.save(category);
        return categoryDto;
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));

        categoryDto.setCompany(mapperUtil.convert(category.getCompany(), new CompanyDto()));
        categoryDto.setId(category.getId());
        Category updatedCategory = mapperUtil.convert(categoryDto, new Category());
        categoryRepository.save(updatedCategory);
        return categoryDto;
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));

        String companyTitle = getLoggedInUser().getCompany().getTitle();
        Long productsCount = categoryRepository.countCategoryProducts(companyTitle, category.getDescription());

        if (productsCount > 0) {
            throw new ServiceException(
                    String.format("This category can not be deleted now. It already has %s %s!.",
                            productsCount,
                            (productsCount == 0) ? "product" : "products"));
        }
        category.setIsDeleted(true);
        categoryRepository.save(category);
    }

    private boolean checkIfHasProducts(CategoryDto categoryDto) {
        String companyTitle = getLoggedInUser().getCompany().getTitle();
        Long productsCount = categoryRepository.countCategoryProducts(companyTitle, categoryDto.getDescription());
        return productsCount > 0;
    }

    private User getLoggedInUser() {
        return securityService.getLoggedInUser();
    }

}
