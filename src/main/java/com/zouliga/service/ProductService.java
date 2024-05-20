package com.zouliga.service;

import com.zouliga.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> listAllProducts(int pageNo, int pageSize);


}
