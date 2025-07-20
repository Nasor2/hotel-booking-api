package com.nasor.bookingapi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestRegistration(
        @NotBlank(message = "First name cannot be empty")
        @Size(max = 100, message = "First name cannot exceed 100 characters")
        String firstName,
        @NotBlank(message = "Last name cannot be empty")
        @Size(max = 100, message = "Last name cannot exceed 100 characters")
        String lastName,
        @NotBlank(message = "Address cannot be empty")
        @Size(max = 255, message = "Address cannot exceed 255 characters")
        String address,
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Email must be a valid email format")
        @Size(max = 255, message = "Email cannot exceed 255 characters")
        String email,
        @NotBlank(message = "Phone number cannot be empty")
        @Size(max = 20, message = "Phone number cannot exceed 20 characters")
        String phoneNumber)
{
}