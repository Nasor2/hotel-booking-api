package com.nasor.bookingapi.dto.booking;

import java.time.LocalDate;

public record BookingRequestRegistration(Long roomId,
                                         Long userId,
                                         LocalDate entryDate,
                                         LocalDate exitDate) {
}
