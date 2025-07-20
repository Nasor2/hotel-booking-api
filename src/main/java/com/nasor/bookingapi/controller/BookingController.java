package com.nasor.bookingapi.controller;

import com.nasor.bookingapi.dto.booking.BookingDto;
import com.nasor.bookingapi.dto.booking.BookingRequestRegistration;
import com.nasor.bookingapi.dto.booking.BookingRequestUpdating;
import com.nasor.bookingapi.service.BookingService;
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
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@Tag(name = "Bookings", description = "Booking Management")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Get All Existing Bookings",
            description = "Retrieves a list of all current bookings in the system.",
            operationId = "getAllBookings"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of bookings",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BookingDto.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.findAllBookings());
    }

    @Operation(summary = "Get Booking by ID",
            description = "Retrieves a specific booking by its unique identifier.",
            operationId = "getBookingById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingDto.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found with the specified ID",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findBookingById(id));
    }

    @Operation(summary = "Create a new Booking",
            description = "Registers a new booking for a room for specified dates and user.",
            operationId = "createBooking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created successfully",
                    headers = @Header(
                            name = "Location",
                            description = "URI of the newly created booking resource",
                            schema = @Schema(type = "string", format = "uri", example = "http://localhost:8080/api/v1/bookings/1")
                    ),

                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid booking details or dates provided",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Room or User not found with the provided IDs",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Booking dates overlap with an existing reservation",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<BookingDto> createBooking(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the booking to be created",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingRequestRegistration.class))
            )
            @RequestBody BookingRequestRegistration requestRegistration) {
        BookingDto booking = bookingService.create(requestRegistration);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(booking.id())
                .toUri();

        return ResponseEntity.created(location).body(booking);
    }

    @Operation(summary = "Update an existing Booking",
            description = "Updates the details of an existing booking identified by its ID.",
            operationId = "updateBookingById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingDto.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found with the specified ID",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid update details or dates provided",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Updated booking dates overlap with an existing reservation",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBookingById(@PathVariable Long id,
                                                    @Valid
                                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                            description = "Updated booking details",
                                                            required = true,
                                                            content = @Content(mediaType = "application/json",
                                                                    schema = @Schema(implementation = BookingRequestUpdating.class))
                                                    )
                                                    @RequestBody BookingRequestUpdating  requestUpdating) {
        return ResponseEntity.ok(bookingService.update(id, requestUpdating));
    }

    @Operation(summary = "Delete Booking by ID",
            description = "Deletes a booking permanently from the system by its ID.",
            operationId = "deleteBookingById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking deleted successfully (No Content)",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking not found with the specified ID",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BookingDto> deleteBookingById(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
