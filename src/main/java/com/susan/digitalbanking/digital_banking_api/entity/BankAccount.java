package com.susan.digitalbanking.digital_banking_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

    @Id
    private String tableid;

    @Column(nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING) //Store enum as text in DB (saving/current (cheque))
    private AccountType type;

    @ManyToOne
    private Customer customer;


}
