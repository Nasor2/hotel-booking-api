package com.nasor.bookingapi.dto.room;

import com.nasor.bookingapi.model.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.DecimalMin;

public record RoomRequestRegistration(
        @NotBlank(message = "Room number cannot be empty")
        String number,
        @NotNull(message = "Room type cannot be null")
        RoomType roomType,
        @NotNull(message = "Price per night cannot be null")
        @Positive(message = "Price per night must be a positive value")
        @DecimalMin(value = "0.01", message = "Price per night must be at least 0.01")
        Double pricePerNight) {
}