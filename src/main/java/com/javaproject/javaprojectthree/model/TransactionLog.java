package com.javaproject.javaprojectthree.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "transactionLog")
@Data
@NoArgsConstructor
public class TransactionLog {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String sender;

    @Column
    private String receiver;

    @Column
    private LocalDate date;

    @Column
    private double amount;

    @Column(columnDefinition = "TEXT")
    private String comment;

    public TransactionLog(String sender, String receiver, LocalDate date, double amount, String comment){
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.amount = amount;
        this.comment = comment;
    }
}