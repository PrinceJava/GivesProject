package com.javaproject.javaprojectthree.controller;


import com.javaproject.javaprojectthree.JavaProjectThreeApplication;
import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.model.forms.LoginRequest;
import com.javaproject.javaprojectthree.model.forms.RegisterForm;
import com.javaproject.javaprojectthree.service.CharityService;
import com.javaproject.javaprojectthree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthenticationController {

    private UserService userService;
    private CharityService charityService;

    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}

    @Autowired
    public void setCharityService(CharityService charityService){this.charityService = charityService;}

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
        return "index";
    }

    @GetMapping("/log_out")
    public String logout(){
        userService.logout();
        return "index";
    }


    @GetMapping("/login")
    public String userLogin(){
        return "login";
    }

    @PostMapping("/register")
    public User createUser(@RequestBody RegisterForm registerForm){
        System.out.println("controller is calling create user ===>");
//        return userService.createUser(registerForm.getFirstName(), registerForm.getLastName(),
//                registerForm.getUserName(),
//                registerForm.getEmailAddress(), registerForm.getPassword(),
//                registerForm.getRole());
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(LoginRequest loginRequest){
        System.out.println("Controller is calling loginUser ===>");
        return userService.loginUser(loginRequest);
    }

}
