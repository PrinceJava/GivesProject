package com.javaproject.javaprojectthree.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}


}
