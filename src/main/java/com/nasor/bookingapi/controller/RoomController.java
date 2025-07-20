package com.nasor.bookingapi.controller;

import com.nasor.bookingapi.dto.room.RoomDto;
import com.nasor.bookingapi.dto.room.RoomRequestRegistration;
import com.nasor.bookingapi.service.RoomService;
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
@RequestMapping("/api/v1/rooms")
@Tag(name = "Rooms", description = "Operations for managing hotel rooms.")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Get All Existing Rooms",
            description = "Retrieves a list of all available rooms in the system.",
            operationId = "getAllRooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of rooms",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RoomDto.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        return  ResponseEntity.ok(roomService.findAll());
    }

    @Operation(summary = "Get Room by ID",
            description = "Retrieves a specific room by its unique identifier.",
            operationId = "getRoomById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomDto.class))),
            @ApiResponse(responseCode = "404", description = "Room not found with the specified ID",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }

    @Operation(summary = "Create a New Room",
            description = "Registers a new room with its details (name, type, price per night).",
            operationId = "createRoom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room created successfully",
                    headers = @Header(
                            name = "Location",
                            description = "URI of the newly created room resource",
                            schema = @Schema(type = "string", format = "uri", example = "http://localhost:8080/api/v1/rooms/1")
                    ),
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid room details provided (e.g., missing name, invalid price)",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Room with provided name already exists",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<RoomDto> createRoom(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Room details for registration",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomRequestRegistration.class))
            )
            @RequestBody RoomRequestRegistration roomRequestRegistration) {
        RoomDto room = roomService.create(roomRequestRegistration);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(room.id())
                .toUri();

        return ResponseEntity.created(location).body(room);
    }

    @Operation(summary = "Update an Existing Room",
            description = "Updates the details of an existing room identified by its ID.",
            operationId = "updateRoom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomDto.class))),
            @ApiResponse(responseCode = "404", description = "Room not found with the specified ID",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid room details provided",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Room with provided name already exists after update",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long id,
                                              @Valid
                                              @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                      description = "Updated room details",
                                                      required = true,
                                                      content = @Content(mediaType = "application/json",
                                                              schema = @Schema(implementation = RoomRequestRegistration.class)) // Asumo que se usa el mismo DTO para update
                                              )
                                              @RequestBody RoomRequestRegistration roomRequestRegistration) {
        return ResponseEntity.ok(roomService.update(id, roomRequestRegistration));
    }

    @Operation(summary = "Delete Room by ID",
            description = "Deletes a room permanently from the system by its ID.",
            operationId = "deleteRoom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Room deleted successfully (No Content)",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Room not found with the specified ID",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
