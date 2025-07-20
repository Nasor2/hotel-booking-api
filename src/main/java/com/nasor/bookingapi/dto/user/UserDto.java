package com.nasor.bookingapi.dto.user;

public record UserDto(Long id,
                      String fullName,
                      String email,
                      String address,
                      String phoneNumber) {
}
