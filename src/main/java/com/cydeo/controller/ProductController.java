package com.cydeo.controller;


import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.ProductDto;
import com.cydeo.entity.Product;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Tag(name = "Products", description = "Endpoints for managing and accessing product-related resources")
@RestController
@RequestMapping(
        value = "/products",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('Admin')")
public class ProductController {

    private final ProductService productService;


    @Operation(
            description = "Retrieve all products",
            summary = "Access all company resources (Admin user only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved companies data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid Token")
            }
    )
    @GetMapping("/list")
    public ResponseEntity<ResponseWrapper> listProducts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        List<ProductDto> productList = productService.listAllProducts(pageNo, pageSize);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .success(true)
                        .message("Successfully retrieved products data")
                        .data(productList)
                        .size(productList.size())
                        .build()
        );
    }
}
















