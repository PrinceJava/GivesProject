package com.javaproject.javaprojectthree.controller;

import com.javaproject.javaprojectthree.JavaProjectThreeApplication;
import com.javaproject.javaprojectthree.exception.InformationNotFoundException;
import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.service.CharityService;
import com.javaproject.javaprojectthree.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/charities")
public class CharityController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    int ROW_PER_PAGE = 5;

    @Autowired
    CharityService charityService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String getCharities(Model model) {
        List<Charity> charities = charityService.findAllCharities();
          model.addAttribute("charities",charities);
          model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
        return "charities";
    }

    @GetMapping("/{charityId}")
    public String findCharityById(
            @PathVariable(value = "charityId") Long charityId,
            Model model){
        model.addAttribute("charity",charityService.findCharityById(charityId));
        model.addAttribute("transactions", charityService.findAllTransactionsByCharityId(charityId));
        return "charity";
    }

    @GetMapping(value = {"/add"})
    public String showAddContact(Model model) {
        if(JavaProjectThreeApplication.myUserDetails != null) {
            Charity charity = new Charity();
            User user = new User();
            model.addAttribute("add", true);
            model.addAttribute("charity", charity);
            model.addAttribute("user", user);
            model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);

            return "charitiesEdit";
        }else {
            return "redirect:/charities/";
        }
    }

    @PostMapping(value = "/add")
    public String addContact(Model model,
                             @ModelAttribute("charity") Charity charity,
                             @ModelAttribute("user") User user) {
        User newUser = userService.findUserByEmailAddress(user.getEmailAddress());
        charity.setUser(newUser);
        try {
            Charity newCharity = charityService.save(charity);
            return "redirect:/charities/" + newCharity.getId();
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", true);
            model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
            return "charitiesEdit";
        }
    }

    @GetMapping(value = {"/charities/{charityId}/edit"})
    public String showEditCharity(Model model, @PathVariable long charityId) {
        Charity charity = null;
        try {
            charity = charityService.findById(charityId);
        } catch (InformationNotFoundException ex) {
            model.addAttribute("errorMessage", "Contact not found");
        }
        model.addAttribute("add", false);
        model.addAttribute("charity", charity);
        model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
        return "charitiesEdit";
    }

    @PostMapping(value = {"/charities/{charityId}/edit"})
    public String updateCharity(Model model,
                                @PathVariable long charityId,
                                @ModelAttribute("charity") Charity charity) {
        try {
            charity.setId(charityId);
            charityService.update(charity);
            return "redirect:/charities/" + charity.getId();
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
            return "charitiesEdit";
        }
    }




    @GetMapping("/{charityId}/checkout")
    public String charityCheckout(
            @PathVariable(value = "charityId") Long charityId,
            Model model){
        model.addAttribute("charity",charityService.findCharityById(charityId));
        model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
        return "checkout.html";
    }
}
