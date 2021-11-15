package com.javaproject.javaprojectthree.service;

import com.javaproject.javaprojectthree.model.Charity;

import java.util.List;

public interface CharityService {
    Charity createCharity(String title, String description, double goal, double totalReceived, Boolean verified, String pictureURL);
    List<Charity> findAllCharities(int pageNumber, int rowPerPage);
    Charity findCharityById(Long id);
    int count();
}
