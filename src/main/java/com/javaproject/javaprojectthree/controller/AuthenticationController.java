package com.javaproject.javaprojectthree.controller;

import com.javaproject.javaprojectthree.JavaProjectThreeApplication;
import com.javaproject.javaprojectthree.exception.InformationNotFoundException;
import com.javaproject.javaprojectthree.model.Role;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.model.forms.LoginRequest;
import com.javaproject.javaprojectthree.model.forms.RegisterUser;
import com.javaproject.javaprojectthree.service.CharityService;
import com.javaproject.javaprojectthree.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Tag(name = "authentication", description = "Endpoints for authentication CRUD operations")
public class AuthenticationController {

    private UserService userService;
    private CharityService charityService;
    private UserDetailsService userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}


    @Autowired
    public void setCharityService(CharityService charityService){this.charityService = charityService;}

    @Operation(summary = "Application homepage",
            description = " This gets the application homepage, " +
                    "Otherwise the homepage did not work.",
            tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved homepage",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid homepage",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
        return "index";
    }

    @Operation(summary = "User can log out",
            description = " This allows the user to log out of their account, " +
                    "Otherwise the user remains logged in",
            tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully logged out ",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
    @GetMapping("/log_out")
    public String logout(){
        userService.logout();
        return "index";
    }

    @Operation(summary = "User can request to login",
            description = " This allows the user to log in into their account, " +
                    "Otherwise the user is not logged in",
            tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully logged in ",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid credentials",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(LoginRequest loginRequest){
        System.out.println("Controller is calling loginUser ===>");
        return userService.loginUser(loginRequest);
    }

    @Operation(summary = "User is logged in",
            description = " This allows the to view their account, " +
                    "Otherwise the user is not logged in",
            tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = " Logged in ",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid credentials",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
    @GetMapping("/login")
    public String userLogin(){
        return "login";
    }



    /*************************** Register ************************************/


    @Operation(summary = "user can register for a new account",
            description = "User can register " +
                    "Otherwise user can login",
            tags = {"authentication"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved page",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid page requested",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })

    @PostMapping("/register")
    public String createUser(@ModelAttribute("user") User user){
        System.out.println(user);
        userService.createUser(
                user.getFirstName(), user.getLastname(),
                user.getUserName(), user.getEmailAddress(),
                user.getPassword(),"ROLE_SENDER");
        System.out.println("controller is calling create user ===>");
        LoginRequest loginRequest = new LoginRequest(user.getEmailAddress(), user.getPassword());
        userService.loginUser(loginRequest);
        return "redirect:/";
    }

    @Operation(summary = "user can select register",
            description = "User can register " +
                    "Otherwise user can login",
            tags = {"authentication"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved page",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid page requested",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
    @GetMapping("/register")
    public String userRegister(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "register";
    }

    @Operation(summary = "user can register for a new account",
            description = "User can register " +
                    "Otherwise user can login",
            tags = {"authentication"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved page",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid page requested",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
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

    @Operation(summary = "user get user profile",
            description = "User can register " +
                    " Otherwise user can go back to homepage",
            tags = {"authentication"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved page",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid page requested",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
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

    @Operation(summary = "user can update account",
            description = "User can update account" +
                    " Otherwise user can",
            tags = {"authentication"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved page",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid page requested",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
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
