package com.nasor.bookingapi.dto.room;

import io.swagger.v3.oas.annotations.media.Schema;

public record RoomDto(
        @Schema(example = "1")
        Long id,
        @Schema(example = "304")
        String number,
        @Schema(example = "STANDARD", description = "Type of the room (e.g., STANDARD, DELUXE)")
        RoomTypeDto roomType,
        @Schema(example = "75.00", description = "Price per night for the room")
        Double pricePerNight) {
}
