package com.nasor.bookingapi.mapper;

import com.nasor.bookingapi.dto.room.RoomDto;
import com.nasor.bookingapi.dto.room.RoomTypeDto;
import com.nasor.bookingapi.model.Room;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RoomDtoMapper implements Function<Room, RoomDto> {

    @Override
    public RoomDto apply(Room room) {
        RoomTypeDto roomTypeDto = new RoomTypeDto(
                room.getType().name(),
                room.getType().getDescription());

        return new RoomDto(
                room.getId(),
                room.getNumber(),
                roomTypeDto,
                room.getPricePerNight());
    }
}
