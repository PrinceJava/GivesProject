package com.javaproject.javaprojectthree.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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
    private double totalReceived;

    @Column
    private Boolean verified;

    @Column
    private boolean goalAchieved = false;

    @Column
    private String pictureURL;

    @Column
    @JsonIgnore
    private String CLIENT_ID;

    @Column
    @JsonIgnore
    private String CLIENT_SECRET;

    @Column
    @JsonIgnore
    private String MODE;

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<TransactionLog> donationList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Charity (String title, String description, double goal, double totalReceived, Boolean verified, String pictureURL){
        this.title = title;
        this.description = description;
        this.goal = goal;
        this.totalReceived = totalReceived;
        this.verified = verified;
        this.pictureURL = pictureURL;
    }
    public Charity (String title, String description, double goal, double totalReceived, Boolean verified, String pictureURL, User user){
        this.title = title;
        this.description = description;
        this.goal = goal;
        this.totalReceived = totalReceived;
        this.verified = verified;
        this.pictureURL = pictureURL;
        this.setUser(user);
    }
    public Charity (String title, String description, double goal, double totalReceived, Boolean verified, String pictureURL, User user, String CLIENT_ID, String CLIENT_SECRET, String MODE){
        this.title = title;
        this.description = description;
        this.goal = goal;
        this.totalReceived = totalReceived;
        this.verified = verified;
        this.pictureURL = pictureURL;
        this.setUser(user);
        this.CLIENT_ID = CLIENT_ID;
        this.CLIENT_SECRET = CLIENT_SECRET;
        this.MODE = MODE;
    }
}
