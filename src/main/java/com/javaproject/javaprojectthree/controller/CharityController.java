package com.javaproject.javaprojectthree.controller;

import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.forms.LoginRequest;
import com.javaproject.javaprojectthree.model.forms.RegisterForm;
import com.javaproject.javaprojectthree.service.CharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/charities")
public class CharityController {

    int ROW_PER_PAGE = 5;

    @Autowired
    CharityService charityService;

    @GetMapping("/")
    public String getCharities(Model model,
                               @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        //List<Charity> charities = charityService.findAllCharities(pageNumber, ROW_PER_PAGE);
        List<Charity> charities = charityService.findAllCharities();
//        int count = charityService.count();
//        boolean hasPrev = pageNumber > 1;
//        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
          model.addAttribute("charities",charities);
//        model.addAttribute("hasPrev",hasPrev);
//        model.addAttribute("prev", pageNumber - 1);
//        model.addAttribute("hasNext",hasNext);
//        model.addAttribute("next",pageNumber + 1);
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

    @GetMapping("/register")
    public String createCharity(){
        return "registerCharity";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCharity(RegisterForm registerForm){
        System.out.println("Controller is calling loginUser ===>");
        return charityService.createCharity(registerForm);
    }
}
