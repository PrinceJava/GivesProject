package com.javaproject.javaprojectthree.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "charity")
public class Charity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDate creationDate;

    @Column
    private int goal;

    @Column
    private boolean goalAchieved = false;

    @JsonIgnore
    @OneToMany(mappedBy = "charity")
    private List<CharityTransaction> charityTransactions;

    @ManyToOne
    @JoinColumn(name = "organizer")
    private User user;

    public Charity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public boolean isGoalAchieved() {
        return goalAchieved;
    }

    public void setGoalAchieved(boolean goalAchieved) {
        this.goalAchieved = goalAchieved;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
