package com.nasor.bookingapi.dto.user;

public record UserRequestRegistration(String firstName,
                                      String lastName,
                                      String address,
                                      String email,
                                      String phoneNumber)
{
}
