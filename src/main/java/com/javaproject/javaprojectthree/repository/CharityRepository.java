package com.javaproject.javaprojectthree.repository;

import com.javaproject.javaprojectthree.model.Charity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharityRepository extends JpaRepository<Charity, Long> {

    Charity findCharityById(Long charityId);
    boolean existsByTitle(String title);
    Charity findByTitle(String charityTitle);

}
