package com.nasor.bookingapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name",  unique = true, nullable = false)
    @Size( max = 255)
    @NotBlank
    private String name;

    @Column(name="room_category", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoomType type;

    @Column(name="price_per_night", nullable = false)
    @PositiveOrZero
    @NotNull
    private Double pricePerNight;


}
