package com.nasor.bookingapi.mapper;

import com.nasor.bookingapi.dto.booking.BookingDto;
import com.nasor.bookingapi.dto.room.RoomDto;
import com.nasor.bookingapi.dto.user.UserDto;
import com.nasor.bookingapi.model.Booking;
import com.nasor.bookingapi.model.Room;
import com.nasor.bookingapi.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BookingDtoMapper implements Function<Booking, BookingDto> {

    private final UserDtoMapper userDtoMapper;
    private final RoomDtoMapper roomDtoMapper;

    public BookingDtoMapper(UserDtoMapper userDtoMapper, RoomDtoMapper roomDtoMapper) {
        this.userDtoMapper = userDtoMapper;
        this.roomDtoMapper = roomDtoMapper;
    }

    @Override
    public BookingDto apply(Booking booking) {
        Room room = booking.getRoom();
        User user = booking.getUser();

        RoomDto roomDto = roomDtoMapper.apply(room);
        UserDto userDto = userDtoMapper.apply(user);

        return new BookingDto(booking.getId(),
                roomDto,
                userDto,
                booking.getEntryDate(),
                booking.getExitDate());
    }
}
