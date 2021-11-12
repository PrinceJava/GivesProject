package com.javaproject.javaprojectthree.repository;

import com.javaproject.javaprojectthree.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // register
    boolean existsByEmailAddress(String userEmailAddress);

    // login
    User findUserByEmailAddress(String userEmailAddress);
    User findUserByUserName(String username);
}
