package com.cydeo.controller;


import com.cydeo.dto.CategoryDto;
import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Tag(name = "Category", description = "Endpoints for managing and accessing category-related resources")
@RestController
@RequestMapping(
        value = "/categories",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
@PreAuthorize("hasRole('Admin')")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            description = "Retrieve all categories",
            summary = "Access all categories resources (Admin user only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved categories data")
            }
    )
    @GetMapping("/list")
    public ResponseEntity<ResponseWrapper> listAllCategories() {

        List<CategoryDto> categoryList = categoryService.listAllCategories();
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .success(true)
                        .message("Successfully retrieved categories data")
                        .code(HttpStatus.OK.value())
                        .data(categoryList)
                        .size(categoryList.size())
                        .build()
        );
    }


    @Operation(
            description = "Create category",
            summary = "Access all categories resources (Admin user only)",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully create category")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseWrapper.builder()
                        .success(true)
                        .message("Successfully created new category for")
                        .code(HttpStatus.CREATED.value())
                        .data(categoryService.create(categoryDto))
                        .build()
                );
    }

    @Operation(
            description = "Update category",
            summary = "Update category resources (Admin user only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated category")
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseWrapper> updateCategory(@PathVariable Long id,
                                                          @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .success(true)
                        .message("Successfully updated new category for")
                        .code(HttpStatus.OK.value())
                        .data(categoryService.update(id,categoryDto))
                        .build()
                );
    }

    @Operation(
            description = "Delete category",
            summary = "Delete category resources (Admin user only)",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Successfully delete category")
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .success(true)
                        .message("Successfully deleted category with id number "+ id)
                        .code(HttpStatus.NO_CONTENT.value())
                        .build()
        );
    }


}


















