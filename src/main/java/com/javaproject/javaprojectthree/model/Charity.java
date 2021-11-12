package com.javaproject.javaprojectthree.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "charity")
@Data
@NoArgsConstructor
public class Charity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private double goal;

    @Column
    private boolean verified;

    @Column
    private boolean goalAchieved = false;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
