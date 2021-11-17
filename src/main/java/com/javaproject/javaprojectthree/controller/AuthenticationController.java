package com.javaproject.javaprojectthree.controller;

import com.javaproject.javaprojectthree.JavaProjectThreeApplication;
import com.javaproject.javaprojectthree.exception.InformationNotFoundException;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.model.forms.LoginRequest;
import com.javaproject.javaprojectthree.model.forms.RegisterUser;
import com.javaproject.javaprojectthree.service.CharityService;
import com.javaproject.javaprojectthree.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthenticationController {

    private UserService userService;
    private CharityService charityService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(LoginRequest loginRequest){
        System.out.println("Controller is calling loginUser ===>");
        return userService.loginUser(loginRequest);
    }

    @GetMapping("/login")
    public String userLogin(){
        return "login";
    }



    /*************************** Register ************************************/



    @PostMapping("/register")
    public User createUser(@RequestBody RegisterUser registerUser){
        System.out.println("controller is calling create user ===>");
        return userService.createUser(registerUser.getUserName(),
        registerUser.getFirstName(), registerUser.getLastName(), registerUser.getEmailAddress(),
        registerUser.getPassword(), registerUser.getRole());
    }

    @GetMapping("/register")
    public String userRegister(){
        return "register";
    }

    @PostMapping(value = "/add")
    public String saveUser(Model model,
                           @ModelAttribute("user") User user) {
        User newUser = userService.findUserByEmailAddress(user.getEmailAddress());
        try {
            User saveUser = userService.saveUser(user);
            return "redirect:/people/" + newUser.getEmailAddress();
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", true);
            model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
            return "register";
        }
    }

    @GetMapping(value = {"/{userId}/edit"})
    public String showEditUser(Model model, @PathVariable long userId) {
        User user = null;
        try {
            user = userService.findUserByEmailAddress(user.getEmailAddress());
        } catch (InformationNotFoundException ex) {
            model.addAttribute("errorMessage", "User not found");
        }
        model.addAttribute("add", false);
        model.addAttribute("user", user);
        model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
        return "register";
    }

    @PostMapping(value = {"/{userId}/edit"})
    public String updateUser(Model model,
                             @PathVariable long userId,
                             @ModelAttribute("charity") User user) {
        try {
            user.setId(userId);
            userService.showEditUser(user);
            return "redirect:/users/" + user.getId();
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            model.addAttribute("User", JavaProjectThreeApplication.myUserDetails);
            return "register";
        }
    }


}
