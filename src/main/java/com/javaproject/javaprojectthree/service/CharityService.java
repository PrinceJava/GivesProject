package com.javaproject.javaprojectthree.service;

import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.model.forms.LoginRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CharityService {
    Charity createCharity(String title, String description, double goal, Boolean verified, String user_id, String pictureURL);
    List<Charity> findAllCharities();
    Charity findCharityById(Long id);
}
