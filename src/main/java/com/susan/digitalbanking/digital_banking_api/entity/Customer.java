package com.susan.digitalbanking.digital_banking_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity                         // Tells JPA: this class = a database table
@Data                           // Lombok: getters, setters, toString, equals, hashCode
@NoArgsConstructor              // JPA needs a no-args constructor
@AllArgsConstructor
public class Customer {

    @Id                         // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment in MySQL
    private Long id;

    @Column(nullable = false)   // DB constraint: cannot be null
    private String name;

    @Column(unique = true, nullable = false) // Email must be unique in DB
    private String email;
}