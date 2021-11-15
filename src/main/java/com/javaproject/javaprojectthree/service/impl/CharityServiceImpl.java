package com.javaproject.javaprojectthree.service.impl;

import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.repository.CharityRepository;
import com.javaproject.javaprojectthree.service.CharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CharityServiceImpl implements CharityService {

    @Autowired
    CharityRepository charityRepository;

    @Override
    public Charity createCharity(String title, String description, double goal, Boolean verified, String user_id, String pictureURL) {
        return null;
    }

    @Override
    public List<Charity> findAllCharities() {
        return charityRepository.findAll();
    }

    @Override
    public Charity findCharityById(Long id) {
        return charityRepository.findCharityById(id);
    }
}
