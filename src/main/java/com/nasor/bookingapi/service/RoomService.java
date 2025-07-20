package com.nasor.bookingapi.service;

import com.nasor.bookingapi.dto.room.RoomDto;
import com.nasor.bookingapi.dto.room.RoomRequestRegistration;
import com.nasor.bookingapi.exception.ResourceNotFound;
import com.nasor.bookingapi.mapper.RoomDtoMapper;
import com.nasor.bookingapi.model.Room;
import com.nasor.bookingapi.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomDtoMapper roomDtoMapper;

    public RoomService(RoomRepository roomRepository, RoomDtoMapper roomDtoMapper) {
        this.roomRepository = roomRepository;
        this.roomDtoMapper = roomDtoMapper;
    }

    public List<RoomDto> findAll() {
        return roomRepository
                .findAll()
                .stream()
                .map(roomDtoMapper)
                .collect(Collectors.toList());
    }

    public RoomDto findById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()) {
            throw new  ResourceNotFound("Room with id " + id + " not found");
        }
        return roomDtoMapper.apply(room.get());
    }

    public RoomDto create(RoomRequestRegistration requestRoom) {
        if (roomRepository.findByNumber(requestRoom.number()).isPresent()){
            throw new IllegalArgumentException("Room with name " + requestRoom.number() + " already exists");
        }
        Room room = new Room();
        room.setNumber(requestRoom.number());
        room.setType(requestRoom.roomType());
        room.setPricePerNight(requestRoom.pricePerNight());

        roomRepository.save(room);

        return roomDtoMapper.apply(room);
    }

    public RoomDto update(Long id, RoomRequestRegistration requestRoom) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()) {
            throw new  ResourceNotFound("Room with id " + id + " not found");
        }

        Room roomToUpdate = room.get();
        roomToUpdate.setNumber(requestRoom.number());
        roomToUpdate.setType(requestRoom.roomType());
        roomToUpdate.setPricePerNight(requestRoom.pricePerNight());

        roomRepository.save(roomToUpdate);

        return roomDtoMapper.apply(roomToUpdate);
    }

    public void delete(Long id) {
        if(roomRepository.findById(id).isEmpty()){
            throw new  ResourceNotFound("Room with id " + id + " not found");
        }
        roomRepository.deleteById(id);
    }
}
