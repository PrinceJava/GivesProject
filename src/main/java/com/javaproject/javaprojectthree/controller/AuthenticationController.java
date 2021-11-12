package com.javaproject.javaprojectthree.controller;


import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.model.forms.LoginRequest;
import com.javaproject.javaprojectthree.model.forms.RegisterForm;
import com.javaproject.javaprojectthree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}

    @PostMapping("/register")
    public User createUser(@RequestBody RegisterForm registerForm){
        System.out.println("controller is calling create user ===>");
        return userService.createUser(registerForm.getUserName(),
                registerForm.getEmailAddress(), registerForm.getPassword(),
                registerForm.getRole());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        System.out.println("Controller is calling loginUser ===>");
        return userService.loginUser(loginRequest);
    }


}