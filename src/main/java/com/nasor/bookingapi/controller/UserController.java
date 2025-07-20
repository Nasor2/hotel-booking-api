package com.nasor.bookingapi.controller;

import com.nasor.bookingapi.dto.user.UserDto;
import com.nasor.bookingapi.dto.user.UserRequestRegistration;
import com.nasor.bookingapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@Tag(name = "Users", description = "Operations for managing user accounts.")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "Get All Existing Users",
            description = "Retrieves a list of all registered users in the system.",
            operationId = "getAllUsers"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return  ResponseEntity.ok(userService.findAll());
    }

    @Operation(summary = "Get User by ID",
            description = "Retrieves a specific user by their unique identifier.",
            operationId = "getUserById"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found with the specified ID",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Create a New User",
            description = "Registers a new user account. The user ID will be generated automatically.",
            operationId = "createUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    headers = @Header(
                            name = "Location",
                            description = "URI of the newly created user resource",
                            schema = @Schema(type = "string", format = "uri", example = "http://localhost:8080/api/v1/users/1")
                    ),
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user details provided",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "User with provided email already exists",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<UserDto> create(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User details for registration",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequestRegistration.class))
            )
            @RequestBody UserRequestRegistration registration) {
        UserDto userDto = userService.create(registration);

        URI location =  ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userDto.id())
                .toUri();

        return ResponseEntity.created(location).body(userDto);
    }

    @Operation(summary = "Update an Existing User",
              description = "Updates the details of an existing user account by its ID.",
            operationId = "updateUser"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found with the specified ID",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid user details provided",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "User with provided email already exists after update",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,
                                          @Valid
                                          @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                  description = "Updated user details",
                                                  required = true,
                                                  content = @Content(mediaType = "application/json",
                                                          schema = @Schema(implementation = UserRequestRegistration.class))
                                          )
                                          @RequestBody UserRequestRegistration registration) {
        UserDto userDto = userService.update(id, registration);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Delete User by ID",
            description = "Deletes a user account permanently from the system by its ID.",
            operationId = "deleteUserById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully (No Content)",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found with the specified ID",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
