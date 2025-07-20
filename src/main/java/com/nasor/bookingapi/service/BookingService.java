package com.nasor.bookingapi.service;

import com.nasor.bookingapi.dto.booking.BookingDto;
import com.nasor.bookingapi.dto.booking.BookingRequestRegistration;
import com.nasor.bookingapi.dto.booking.BookingRequestUpdating;
import com.nasor.bookingapi.exception.ResourceNotFound;
import com.nasor.bookingapi.exception.RoomNotAvailableException;
import com.nasor.bookingapi.mapper.BookingDtoMapper;
import com.nasor.bookingapi.model.Booking;
import com.nasor.bookingapi.model.Room;
import com.nasor.bookingapi.model.User;
import com.nasor.bookingapi.repository.BookingRepository;
import com.nasor.bookingapi.repository.RoomRepository;
import com.nasor.bookingapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingDtoMapper bookingDtoMapper;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, BookingDtoMapper bookingDtoMapper, RoomRepository roomRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingDtoMapper = bookingDtoMapper;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<BookingDto> findAllBookings() {
        return bookingRepository
                .findAll().stream()
                .map(bookingDtoMapper)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookingDto> findBookingsByRoom(Long roomId) {
        if (bookingRepository.findById(roomId).isEmpty()) {
            throw new ResourceNotFound("Room not found");
        }

        return bookingRepository.findAllByRoomId(roomId)
                .stream()
                .map(bookingDtoMapper)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookingDto findBookingById(Long bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) {
            throw new ResourceNotFound("Booking not found");
        }
        return bookingDtoMapper.apply(booking.get());
    }

    @Transactional(readOnly = true)
    public boolean isRoomAvailable(Long roomId, LocalDate entryDate, LocalDate exitDate) {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(roomId, entryDate, exitDate);
        return overlappingBookings.isEmpty();
    }

    @Transactional(readOnly = true)
    public boolean isRoomAvailableForUpdate(Long roomId, LocalDate entryDate, LocalDate exitDate, Long bookingIdToExclude) {
        List<Booking> bookings = bookingRepository.findOverlappingBookingsExcludingCurrent(roomId, entryDate, exitDate, bookingIdToExclude);

        return bookings.isEmpty();
    }

    @Transactional
    public BookingDto create(BookingRequestRegistration request) {
        if (request.exitDate().isBefore(request.entryDate())
                || request.entryDate().isEqual(request.exitDate())) {
            throw new IllegalArgumentException("Invalid dates");
        }

        Optional<Room> room = roomRepository.findById(request.roomId());
        Optional<User> user = userRepository.findById(request.userId());

        if (room.isEmpty()){
            throw new ResourceNotFound("Room not found");
        }
        if (user.isEmpty()) {
            throw new ResourceNotFound("User not found");
        }

        if(!isRoomAvailable(room.get().getId(), request.entryDate(), request.exitDate())) {
            throw new RoomNotAvailableException("Invalid dates");
        }

        Booking booking = new Booking();
        booking.setRoom(room.get());
        booking.setUser(user.get());
        booking.setExitDate(request.exitDate());
        booking.setEntryDate(request.entryDate());

        bookingRepository.save(booking);

        return bookingDtoMapper.apply(booking);
    }

    @Transactional
    public BookingDto update(Long id, BookingRequestUpdating requestUpdating) {
        Optional<Booking> booking = bookingRepository.findById(id);

        if (booking.isEmpty()) {
            throw new ResourceNotFound("Booking not found");
        }

        if (requestUpdating.exitDate().isBefore(requestUpdating.entryDate())
        || requestUpdating.exitDate().isEqual(requestUpdating.entryDate())) {
            throw new IllegalArgumentException("Invalid dates");
        }

        Optional<Room> existingRoom = roomRepository.findById(requestUpdating.roomId());
        if (existingRoom.isEmpty()) {
            throw new ResourceNotFound("Room not found");
        }

        Room room = existingRoom.get();

        if (!isRoomAvailableForUpdate(room.getId(), requestUpdating.entryDate(), requestUpdating.exitDate(), id)){
            throw new RoomNotAvailableException("Invalid dates");
        }

        Booking bookingToUpdate = booking.get();

        bookingToUpdate.setRoom(room);
        bookingToUpdate.setExitDate(requestUpdating.exitDate());
        bookingToUpdate.setEntryDate(requestUpdating.entryDate());

        bookingRepository.save(bookingToUpdate);

        return bookingDtoMapper.apply(bookingToUpdate);
    }

    @Transactional
    public void delete(Long bookingId) {
        if(bookingRepository.findById(bookingId).isEmpty()) {
            throw new ResourceNotFound("Booking not found");
        }
        bookingRepository.deleteById(bookingId);
    }

}

