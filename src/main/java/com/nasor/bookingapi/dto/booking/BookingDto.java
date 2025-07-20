package com.nasor.bookingapi.dto.booking;

import com.nasor.bookingapi.dto.room.RoomDto;
import com.nasor.bookingapi.dto.user.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record BookingDto(
        @Schema(example = "1")
        Long id,
        @Schema(description = "Details of the room associated with the booking")
        RoomDto room,
        @Schema(description = "Details of the user who made the booking")
        UserDto user,
        @Schema(example = "2025-08-01", description = "The date the booking starts")
        LocalDate entryDate,
        @Schema(example = "2025-08-07", description = "The date the booking ends")
        LocalDate exitDate) {
}
