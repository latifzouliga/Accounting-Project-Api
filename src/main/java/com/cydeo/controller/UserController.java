package com.cydeo.controller;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.UserService;
import io.swagger.v3.core.util.Json;
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

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
     *
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

    /**
     * {@link UserService#update(UserDto)}
     *
     * @param userDto
     */
    @Operation(
            description = "Put endpoint for updating user",
            summary = "Only Root and Admin with Root or Admin Roles can execute this endpoint",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "401")
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
                        .message("Users updated successfully")
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
            description = "Put endpoint for updating user",
            summary = "Only Root and Admin with Root or Admin Roles can execute this endpoint",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = """
                            Please enter the field needs to be updated as key and value
                            </br></br></br>
                            Example: <strong>{"firstname":"name"}</strong>
                            """,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HashMap.class)
                    )
            ),
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
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
                        .message("Users updated successfully")
                        .data(user)
                        .build()
        );
    }

    /**
     * 1.  User should be able to delete users through the "User List" page list items
     *     When user clicks on the "Delete" button, the user should be deleted.
     *
     * 2. If the user is the only "Admin" of his/her company, delete button should be disabled and when user hover over
     *    on this button, it should show "Can not be deleted! This user is only admin for this company or logged in admin.
     *    " (most part of this feature is from html, backend should send true for isOnlyAdmin field with userDto to disable
     *    delete button and show the tooltip message.).
     *
     * 3. Email should be modified before deletion so that new user can be created with same email.
     *
     * 4. After deletion process, user should be able to stay on the "User List" page with refreshed information.
     *
     * @return
     */

    @Operation(
            description = "Delete endpoint for deleting user",
            summary = "Only Admin with Root or Admin Role can execute this endpoint",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "401")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable String username){
        userService.delete(username);
        return ResponseEntity.ok(
                ResponseWrapper.builder().success(true).message("User deleted successfully").build()
        );
    }

    @PreAuthorize("hasAnyRole('Admin', 'Root')")
    @GetMapping
    public ResponseEntity<List<UserDto>> get() {
        return ResponseEntity.ok(userService.listAllUsersByCompany("Blue Tech"));
    }


}