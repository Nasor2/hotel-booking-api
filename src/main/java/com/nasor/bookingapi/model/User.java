package com.nasor.bookingapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name",  nullable = false)
    @NotBlank
    @Size(max = 255)
    private String firstName;

    @Column(name = "last_name",  nullable = false)
    @NotBlank
    @Size(max = 255)
    private String lastName;

    @Column(name = "address")
    @NotBlank
    @Size(max = 255)
    private String address;

    @Column(name = "email", nullable = false)
    @NotBlank
    @Size(max = 255)
    @Email
    private String email;

    @Column(name = "phone_number",  nullable = false)
    @NotBlank
    @Size(max = 255)
    private String phoneNumber;
}
