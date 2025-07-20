package com.nasor.bookingapi.model;

public enum RoomType {
    STANDARD("Room with all the basic needs."),
    DELUXE("Room with beautiful views and service VIP");

    private final String description;

    RoomType(String description) {
        this.description = description;
    }

    private String getDescription() {
        return description;
    }
}
