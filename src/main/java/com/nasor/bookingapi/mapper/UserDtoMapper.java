package com.nasor.bookingapi.mapper;

import com.nasor.bookingapi.dto.user.UserDto;
import com.nasor.bookingapi.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDtoMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(user.getId(), user.getFirstName() + " " + user.getLastName(), user.getEmail(), user.getAddress(), user.getPhoneNumber());
    }
}
