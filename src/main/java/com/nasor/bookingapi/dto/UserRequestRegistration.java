package com.nasor.bookingapi.dto;

public record UserRequestRegistration(String firstName,
                                      String lastName,
                                      String address,
                                      String email,
                                      String phoneNumber)
{
}
