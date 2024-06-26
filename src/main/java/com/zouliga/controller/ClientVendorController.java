package com.zouliga.controller;

import com.zouliga.dto.ClientVendorDto;
import com.zouliga.entity.ResponseWrapper;
import com.zouliga.service.ClientVendorService;
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
import java.util.Map;

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
            description = "Retrieve all clients/vendors",
            summary = "Access all clients/vendors resources (Root user only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved clients-vendors data")
            }
    )
    @GetMapping("/list")
    public ResponseEntity<ResponseWrapper> listAllClientsAndVendors(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

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
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved clients/vendors data")
            }
    )
    @GetMapping("/list/{clientVendorType}")
    public ResponseEntity<ResponseWrapper> listAllClients(
            @PathVariable String clientVendorType,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        List<ClientVendorDto> clientVendorList = clientVendorService.listAllByClientVendorType(clientVendorType, pageNo, pageSize);
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
            description = "Create clientVendor",
            summary = "Create clientVendor (Admin user only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully created clientVendor")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> createClientVendor(@RequestBody ClientVendorDto clientVendorDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ResponseWrapper.builder()
                                .success(true)
                                .message("Successfully created clientVendor")
                                .code(HttpStatus.OK.value())
                                .data(clientVendorService.create(clientVendorDto))
                                .build()
                );
    }

    @Operation(
            description = "Update clientVendor",
            summary = "Create clientVendor (Admin user only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated clientVendor")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseWrapper> updateClientVendor(@PathVariable Long id,
                                                              @RequestBody ClientVendorDto clientVendorDto) {
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .success(true)
                        .message("Successfully updated clientVendor")
                        .code(HttpStatus.OK.value())
                        .data(clientVendorService.update(id, clientVendorDto))
                        .build()
        );
    }


    @Operation(
            description = "Patch clientVendor",
            summary = "Create clientVendor (Admin user only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated clientVendor")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @PatchMapping("/patch/{id}")
    public ResponseEntity<ResponseWrapper> patchClientVendor(@PathVariable Long id,
                                                             @RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .success(true)
                        .message("Successfully updated clientVendor field/s")
                        .code(HttpStatus.OK.value())
                        .data(clientVendorService.patch(id, fields))
                        .build()
        );
    }

    @Operation(
            description = "Delete clientVendor",
            summary = "Create clientVendor (Admin user only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted clientVendor")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper> deleteClientVendor(@PathVariable Long id) {
        clientVendorService.delete(id);
        ClientVendorDto clientVendorDto = clientVendorService.findById(id);
        return ResponseEntity
                .ok(ResponseWrapper.builder()
                        .success(true)
                        .message(String.format("Successfully deleted %s %s",
                                clientVendorDto.getClientVendorName(),
                                clientVendorDto.getClientVendorType().getValue()))
                        .code(HttpStatus.NO_CONTENT.value())
                        .build()
                );
    }

}














