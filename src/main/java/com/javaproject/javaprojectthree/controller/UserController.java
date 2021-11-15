package com.javaproject.javaprojectthree.controller;
import com.javaproject.javaprojectthree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}


    @GetMapping({"/all", "/people"})
    public String findAllUsers(Model model){
        model.addAttribute("listUsers",userService.findAllUsers());
        return "people";
    }

    @GetMapping("/{userId}")
    public String findUserById(
            @PathVariable(value = "userId") Long userId,
            Model model){
        model.addAttribute("user",userService.findUserById(userId));
        return "person";
    }
}
