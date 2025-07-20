package com.nasor.bookingapi.dto.room;

import com.nasor.bookingapi.model.RoomType;

public record RoomRequestRegistration(String number,
                                      RoomType roomType,
                                      Double pricePerNight) {
}
