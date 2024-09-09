package com.charly.demospringbootjsp.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false)
    @NonNull
    private String email;

    @Column(nullable = false)
    @NonNull
    private String country;

    @Column(nullable = false)
    @NonNull
    private String phone;

    @Column(nullable = false)
    private Boolean isActive = true;
}
