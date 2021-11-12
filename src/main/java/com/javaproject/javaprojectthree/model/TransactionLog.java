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
    private String from;

    @Column
    private String to;

    @Column
    private double amount;

    @Column
    private LocalDate creationDate;

    @Column(columnDefinition = "text")
    private String comment;

}