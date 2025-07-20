package com.nasor.bookingapi.model;

import lombok.Getter;

@Getter
public enum RoomType {
    STANDARD("Room with all the basic needs."),
    DELUXE("Room with beautiful views and service VIP");

    private final String description;

    RoomType(String description) {
        this.description = description;
    }

}
