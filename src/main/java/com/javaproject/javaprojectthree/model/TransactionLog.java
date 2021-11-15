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
    private String title;

    @Column
    private String sender;

    @Column
    private String receiver;

    @Column
    private LocalDate date;

    @Column(columnDefinition = "TEXT")
    private String comment;
}