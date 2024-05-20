package com.zouliga.controller;

import com.zouliga.dto.CompanyDto;
import com.zouliga.entity.ResponseWrapper;
import com.zouliga.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Tag(name = "Companies", description = "Endpoints for managing and accessing company-related resources")
@RestController
@RequestMapping(
        value = "/companies",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('Root User')")
public class CompanyController {

    private final CompanyService companyService;


    @Operation(
            description = "Retrieve all companies",
            summary = "Access all company resources (Root user only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved companies data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid Token")
            }
    )
    @GetMapping("/list")
    public ResponseEntity<ResponseWrapper> listCompanies(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        List<CompanyDto> companyList = companyService.listAllCompanies(pageNo, pageSize);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .success(true)
                        .message("Successfully retrieved companies data")
                        .data(companyList)
                        .size(companyList.size())
                        .build()
        );
    }


    @Operation(
            description = "Retrieve company by id",
            summary = "Access one company resources (Root user only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved company data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid Token")
            }
    )
    @GetMapping("/list/{id}")
    public ResponseEntity<ResponseWrapper> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .success(true)
                        .message(String.format("Successfully retrieved company with %s data", id))
                        .data(companyService.findCompanyById(id))
                        .build()
        );
    }

    @Operation(
            description = "Create company",
            summary = "Create company (Root user only)",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Company created successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid Token")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> createCompany(@Valid @RequestBody CompanyDto companyDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .success(true)
                        .message(String.format("Successfully created %s company data", companyDto.getTitle()))
                        .data(companyService.create(companyDto))
                        .build()
                );
    }

    @Operation(
            description = "update company",
            summary = "Create company (Root user only)",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Company updated successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid Token")
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseWrapper> updateCompany(@Valid@ PathVariable Long id,
                                                         @RequestBody CompanyDto companyDto) {
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .success(true)
                        .message(String.format("Successfully updated %s company data", companyDto.getTitle()))
                        .data(companyService.update(id, companyDto))
                        .build()
        );
    }

    @Operation(
            description = "update company status",
            summary = "update company status (Root user only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Company status updated successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid Token")
            }
    )
    @PutMapping("/update-status/{companyId}/{companyStatus}")
    public ResponseEntity<ResponseWrapper> activateCompany(@PathVariable Long companyId,
                                                           @PathVariable String companyStatus) {
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .success(true)
                        .message(String.format("Successfully updated company %s to %s", companyId, companyStatus))
                        .data(companyService.activateDeactivate(companyId, companyStatus))
                        .build()
        );
    }


}













