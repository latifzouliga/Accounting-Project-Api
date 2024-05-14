package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.ClientVendorService;
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
@Tag(name = "Client Vendor", description = "Endpoints for managing and accessing client-vendor-related resources")
@RestController
@RequestMapping(
        value = "/client-vendor",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('Admin')")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;

    @Operation(
            description = "Retrieve all companies",
            summary = "Access all company resources (Root user only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved clients-vendors data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid Token")
            }
    )
    @GetMapping("/list")
    public ResponseEntity<ResponseWrapper> listAllClientsAndVendors(
            @RequestParam(value = "pageNo",defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){

        List<ClientVendorDto> clientVendorList = clientVendorService.listAllClientVendors(pageNo, pageSize);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .success(true)
                        .message("Successfully retrieved clients-vendors data")
                        .code(HttpStatus.OK.value())
                        .data(clientVendorList)
                        .size(clientVendorList.size())
                        .build()
        );

    }

    @Operation(
            description = "Retrieve all clients or vendors using '/list/client' for clients and '/list/vendor' for vendors",
            summary = "Access all clients/vendors resources,  (Admin user only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved clients/vendors data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid Token")
            }
    )
    @GetMapping("/list/{clientVendorType}")
    public ResponseEntity<ResponseWrapper> listAllClients(
            @PathVariable String clientVendorType,
            @RequestParam(value = "pageNo",defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){

        List<ClientVendorDto> clientVendorList = clientVendorService.listAllClientsClientVendorType(clientVendorType,pageNo, pageSize);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .success(true)
                        .message("Successfully retrieved clients-vendors data")
                        .code(HttpStatus.OK.value())
                        .data(clientVendorList)
                        .size(clientVendorList.size())
                        .build()
        );

    }
}














