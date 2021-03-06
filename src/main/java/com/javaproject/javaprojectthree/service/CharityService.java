package com.javaproject.javaprojectthree.service;

import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.TransactionLog;
import com.javaproject.javaprojectthree.model.forms.RegisterForm;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CharityService {
    Charity createCharity(String title, String description, double goal, double totalReceived, Boolean verified, String pictureURL);
    void deleteCharity(Long charityId);

    List<Charity> findAllCharities();
    Charity findCharityById(Long id);
    List<TransactionLog> findAllTransactionsByCharityId(Long charityId);
    Charity save(Charity charity);
    void update(Charity charity);
    Charity findById(long charityId);

}
