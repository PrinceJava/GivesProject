package com.javaproject.javaprojectthree.service;

import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.TransactionLog;
import com.javaproject.javaprojectthree.model.forms.RegisterForm;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CharityService {
    Charity createCharity(String title, String description, double goal, double totalReceived, Boolean verified, String pictureURL);
    ResponseEntity<?> createCharity(RegisterForm registerForm);
    List<Charity> findAllCharities(int pageNumber, int rowPerPage);
    Charity findCharityById(Long id);
    int count();
    List<TransactionLog> findAllTransactionsByCharityId(Long charityId);
}
