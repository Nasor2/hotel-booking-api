package com.nasor.bookingapi.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserDto(
        @Schema(example = "1")
        Long id,
        @Schema(example = "Alice Smith")
        String fullName,
        @Schema(example = "alice@example.com")
        String email,
        @Schema(example = "123 Main St, Anytown")
        String address,
        @Schema(example = "3425067926")
        String phoneNumber) {
}
