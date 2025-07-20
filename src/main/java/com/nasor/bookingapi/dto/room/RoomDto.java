package com.nasor.bookingapi.dto.room;

public record RoomDto(Long id,
                      String number,
                      RoomTypeDto roomType,
                      Double pricePerNight) {
}
