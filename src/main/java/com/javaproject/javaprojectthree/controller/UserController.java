package com.javaproject.javaprojectthree.controller;
import com.javaproject.javaprojectthree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}

    @GetMapping("/all")
    public String findAllUsers(Model model){
        model.addAttribute("listUsers",userService.findAllUsers());
        return "people";
    }

}
