package com.nasor.bookingapi.dto.room;

public record RoomDto(Long id,
                      String name,
                      RoomTypeDto roomType,
                      Double pricePerNight) {
}
