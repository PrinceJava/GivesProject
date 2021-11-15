package com.javaproject.javaprojectthree.service;


import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.Role;
import com.javaproject.javaprojectthree.model.User;

public interface InitService {

    // Data Base Logic Methods
    public User addUser(User userObject);
    public Role addRole(Role roleObject);
    public void addUserRole(String username, String roleName);
    public Charity createCharity(String title, String description, double goal, double totalReceived, Boolean verified, String user_id, String pictureURL);

    }
