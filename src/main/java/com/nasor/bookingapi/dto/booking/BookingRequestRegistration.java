package com.nasor.bookingapi.dto.booking;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record BookingRequestRegistration(
        @NotNull(message = "Room ID cannot be null")
        Long roomId,
        @NotNull(message = "User ID cannot be null")
        Long userId,
        @NotNull(message = "Entry date cannot be null")
        @FutureOrPresent(message = "Entry date must be today or in the future")
        LocalDate entryDate,
        @NotNull(message = "Exit date cannot be null")
        @FutureOrPresent(message = "Exit date must be today or in the future")
        LocalDate exitDate) {
}