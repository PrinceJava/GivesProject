package com.javaproject.javaprojectthree.repository;

import com.javaproject.javaprojectthree.model.Charity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharityRepository extends JpaRepository<Charity, Long> {

    Charity findCharityById(Long charityId);
}
