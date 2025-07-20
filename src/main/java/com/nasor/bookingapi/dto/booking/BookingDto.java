package com.nasor.bookingapi.dto.booking;

import com.nasor.bookingapi.dto.room.RoomDto;
import com.nasor.bookingapi.dto.user.UserDto;

import java.time.LocalDate;

public record BookingDto(Long id,
                         RoomDto room,
                         UserDto user,
                         LocalDate entryDate,
                         LocalDate exitDate) {
}
