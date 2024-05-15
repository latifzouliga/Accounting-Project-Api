package com.cydeo.controller;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Users", description = "Endpoints for managing and accessing user-related resources")
@RequestMapping(
        value = "/users",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(
            description = "Save user",
            summary = "Access all user resources (Root and Admin users only)",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User saved successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid Token")
            }
    )
    @PreAuthorize("hasAnyRole('Admin', 'Root')")
    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> create(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.create(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResponseWrapper.builder()
                        .code(HttpStatus.CREATED.value())
                        .success(true)
                        .message(String.format("User %s created successfully", user.getUsername()))
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
            description = "Retrieve all users",
            summary = "Access all user resources (Root and Admin users only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved users data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid Token")
            }
    )
    @PreAuthorize("hasAnyRole('Root','Admin')")
    @GetMapping(value = "/list", produces = {"application/json", "application/xml"})
    public ResponseEntity<ResponseWrapper> getAllFilteredUsers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

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

    /**
     * {@link UserService#update(UserDto)}
     *
     * @param userDto
     */
    @Operation(
            description = "Update user",
            summary = "Access all user resources (Root and Admin users only)",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User updated successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid Token")
            }
    )
    @PreAuthorize("hasAnyRole('Root','Admin')")
    @PutMapping("/update")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDto userDto) {
        UserDto user = userService.update(userDto);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .success(true)
                        .message(String.format("User %s updated successfully", user.getUsername()))
                        .data(user)
                        .build()
        );
    }

    /**
     * This method can be used to update one or more fields
     * {@link UserService#update(String, Map)}
     *
     * @param username
     * @param field
     */
    @Operation(
            description = "Update user details",
            summary = "Update user information (Root or Admin with Root/Admin Roles only)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Please provide the fields to update as key-value pairs.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(description = "User details updated successfully", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "401")
            }
    )

    @PreAuthorize("hasAnyRole('Root','Admin')")
    @PatchMapping("/update/{username}")
    public ResponseEntity<ResponseWrapper> patchUser(@Valid @PathVariable String username,
                                                     @RequestBody() Map<String, Object> field) {

        UserDto user = userService.update(username, field);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .success(true)
                        .message(String.format("User %s updated successfully", user.getUsername()))
                        .data(user)
                        .build()
        );
    }


    /**
     * {@link UserService#delete(String)}
     * Admin can delete users if:
     * - user is not the only admin in his company
     * - user is not loggedin user
     * - user is not the root user
     *
     * @param username
     * @return userDto
     */
    @Operation(
            description = "Delete user",
            summary = "Delete a user account (Admin with Root/Admin Roles only)",
            responses = {
                    @ApiResponse(description = "User deleted successfully", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "401")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable String username) {
        userService.delete(username);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        ResponseWrapper.builder()
                                .success(true)
                                .code(HttpStatus.NO_CONTENT.value())
                                .message(String.format("User %s updated successfully", username))
                                .build()
                );
    }

    /**
     * {@link UserService#findByUsername(String)}
     *
     * @param username Admin can view any user in his company
     *                 Root User can only search users with admin roles
     */
    @Operation(
            description = "Retrieve user details",
            summary = "Retrieve user information (Admin with Root/Admin Roles only)",
            responses = {
                    @ApiResponse(description = "User details retrieved successfully", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "401")
            }
    )
    @PreAuthorize("hasAnyRole('Admin', 'Root')")
    @GetMapping("/{username}")
    public ResponseEntity<ResponseWrapper> getUser(@PathVariable String username) {
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .message(String.format("User %s retrieved successfully", username))
                        .success(true)
                        .data(userService.findByUsername(username))
                        .build()
        );
    }


}