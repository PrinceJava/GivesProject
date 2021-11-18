package com.javaproject.javaprojectthree.service;

import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.model.forms.LoginRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User createUser(String firstName, String lastName, String userName, String emailAddress, String password, String roleName);
    ResponseEntity<?> loginUser(LoginRequest loginRequest);
    List<User> findAllUsers();
    User findUserByEmailAddress(String email);
    User findUserById(Long id);
    void logout();
    User saveUser(User user);
    User showEditUser(User user);

}
