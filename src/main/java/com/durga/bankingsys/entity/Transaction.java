package com.durga.bankingsys.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;// type of transactions(Withdraw, Transfer, Deposit)
    private Double amount;
    private LocalDateTime time;

    @ManyToOne
    private Account account;
}
