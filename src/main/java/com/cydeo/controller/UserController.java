package com.cydeo.controller;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Create User: {@link UserService#save(UserDto)}
     * @param userDto
     */
    @Operation(
            description = "Post endpoint for creating user",
            summary = "Only User with Root or Admin Role can execute this endpoint",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201"),
                    @ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "401")
            }
    )
    @PreAuthorize("hasAnyRole('Admin', 'Root')")
    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> create(@RequestBody UserDto userDto) {
        UserDto user = userService.save(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResponseWrapper.builder()
                        .code(HttpStatus.CREATED.value())
                        .success(true)
                        .message("User created successfully")
                        .data(user)
                        .build()
                );
    }
    


    /**
     * if the logged in user is Root, the all admins will be listed
     * if the logged in user is Admin, the all users of the Admin company will be listed
     * List All Filtered User: {@link UserService#listAllFilteredUsers(int, int)
     */
    @Operation(
            description = "Get endpoint for listing users",
            summary = "Only Root and Admin with Root or Admin Roles can execute this endpoint",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "401")
            }
    )
    @PreAuthorize("hasAnyRole('Root','Admin')")
    @GetMapping("/list")
    public ResponseEntity<ResponseWrapper> getAllFilteredUsers(
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        List<UserDto> userList = userService.listAllFilteredUsers(pageNo, pageSize);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .success(true)
                        .message("Users fetched successfully")
                        .data(userList)
                        .size(userList.size())
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('Admin', 'Root')")
    @GetMapping
    public ResponseEntity<List<UserDto>> get() {
        return ResponseEntity.ok(userService.listAllUsersByCompany("Blue Tech"));
    }


}