package com.nasor.bookingapi.dto.booking;

import java.time.LocalDate;

public record BookingRequestUpdating(Long roomId,
                                     LocalDate entryDate,
                                     LocalDate exitDate) {
}
