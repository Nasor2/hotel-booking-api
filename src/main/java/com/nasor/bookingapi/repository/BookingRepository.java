package com.nasor.bookingapi.repository;

import com.nasor.bookingapi.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Get all overlapping bookings to check if available room
    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId " +
            "AND b.entryDate <= :exitDate AND b.exitDate >= :entryDate")
    List<Booking> findOverlappingBookings(@Param("roomId") Long roomId, @Param("entryDate")LocalDate entryDate, @Param("exitDate") LocalDate exitDate);

    // Get all overlapping bookings excluding one to check if can update booking
    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId  " +
            "AND b.entryDate <= :exitDate AND b.exitDate >= :entryDate " +
            "AND b.id != :bookingIdToExclude")
    List<Booking> findOverlappingBookingsExcludingCurrent(@Param("roomId") Long roomId,
                                                          @Param("entryDate")LocalDate entryDate,
                                                          @Param("exitDate") LocalDate exitDate,
                                                          @Param("bookingIdToExclude") Long bookingIdToExclude);

    List<Booking> findAllByRoomId(Long roomId);
}
