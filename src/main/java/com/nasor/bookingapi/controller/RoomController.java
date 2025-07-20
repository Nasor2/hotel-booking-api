package com.nasor.bookingapi.controller;

import com.nasor.bookingapi.dto.room.RoomDto;
import com.nasor.bookingapi.dto.room.RoomRequestRegistration;
import com.nasor.bookingapi.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        return  ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomRequestRegistration roomRequestRegistration) {
        RoomDto room = roomService.create(roomRequestRegistration);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(room.id())
                .toUri();

        return ResponseEntity.created(location).body(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long id, @RequestBody RoomRequestRegistration roomRequestRegistration) {
        return ResponseEntity.ok(roomService.update(id, roomRequestRegistration));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
