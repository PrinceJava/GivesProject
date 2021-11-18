package com.javaproject.javaprojectthree.controller;
import com.javaproject.javaprojectthree.JavaProjectThreeApplication;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@Tag(name = "User", description = "Endpoints for user CRUD operations")
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}


    @Operation(summary = "Get all the users",
            description = "User can list all the Users. This end point returns list of Users, " +
                    "Otherwise an empty list.",
            tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid list supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
    @GetMapping({"/all", "/people"})
    public String findAllUsers(Model model){
        model.addAttribute("listUsers",userService.findAllUsers());
        model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
        return "people";
    }

    @Operation(summary = "Get a single user",
            description = "This end point returns a single user, " +
                    "Otherwise an empty list.",
            tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid list supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
    @GetMapping("/{userId}")
    public String findUserById(
            @PathVariable(value = "userId") Long userId,
            Model model){
        model.addAttribute("user",userService.findUserById(userId));
        model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
        return "person";
    }
}
