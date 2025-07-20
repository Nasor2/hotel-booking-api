package com.nasor.bookingapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @NotNull
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(name = "entry_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private LocalDate entryDate;

    @Column(name = "exit_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private LocalDate exitDate;
}
