package com.nasor.bookingapi.controller;

import com.nasor.bookingapi.dto.booking.BookingDto;
import com.nasor.bookingapi.dto.booking.BookingRequestRegistration;
import com.nasor.bookingapi.dto.booking.BookingRequestUpdating;
import com.nasor.bookingapi.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.findAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findBookingById(id));
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingRequestRegistration requestRegistration) {
        BookingDto booking = bookingService.create(requestRegistration);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(booking.id())
                .toUri();

        return ResponseEntity.created(location).body(booking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id, @RequestBody BookingRequestUpdating  requestUpdating) {
        return ResponseEntity.ok(bookingService.update(id, requestUpdating));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingDto> deleteBooking(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
