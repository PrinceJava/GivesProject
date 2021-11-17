package com.javaproject.javaprojectthree.controller;

import com.javaproject.javaprojectthree.JavaProjectThreeApplication;
import com.javaproject.javaprojectthree.exception.InformationNotFoundException;
import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.TransactionLog;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.service.CharityService;
import com.javaproject.javaprojectthree.service.TransactionLogService;
import com.javaproject.javaprojectthree.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionLogController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CharityService charityService;

    @Autowired
    UserService userService;

    @Autowired
    TransactionLogService transactionLogService;

    @GetMapping("/")
    public String findAllTransactions(Model model) {
        List<TransactionLog> transactions = transactionLogService.findAllTransactions();
          model.addAttribute("transactions",transactions);
          model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
        return "transactions";
    }

    @GetMapping("/{transactionId}")
    public String findCharityById(
            @PathVariable(value = "transactionId") Long transactionId,
            Model model){
        model.addAttribute("transaction",transactionLogService.findTransactionById(transactionId));
        model.addAttribute("charity", transactionLogService.findCharityByReceiver(transactionId));
        model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
        return "transaction";
    }

    @GetMapping(value = {"/add"})
    public String showAddTransaction(Model model) {
        if(JavaProjectThreeApplication.myUserDetails != null) {
            TransactionLog transaction = new TransactionLog();
            User user = new User();
            model.addAttribute("add", true);
            model.addAttribute("transaction", transaction);
            model.addAttribute("user", user);
            model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);

            return "transactionsEdit";
        }else {
            return "redirect:/transactions/";
        }
    }

    @PostMapping(value = "/add")
    public String addContact(Model model,
                             @ModelAttribute("transaction") TransactionLog transaction,
                             @ModelAttribute("user") User user) {
        User newUser = userService.findUserByEmailAddress(transaction.getSender());
        transaction.setSender(newUser.getUserName());
        transaction.setDate(LocalDate.now());
        try {
            TransactionLog transactionLog = transactionLogService.save(transaction);
            return "redirect:/transactions/" + transactionLog.getId();
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", true);
            model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
            return "transactionsEdit";
        }
    }

    @GetMapping(value = {"/transactions/{transactionId}/edit"})
    public String showEditCharity(Model model, @PathVariable long transactionId) {
        TransactionLog transactionLog = null;
        try {
            transactionLog = transactionLogService.findTransactionById(transactionId);
        } catch (InformationNotFoundException ex) {
            model.addAttribute("errorMessage", "Transaction not found");
        }
        model.addAttribute("add", false);
        model.addAttribute("transaction", transactionLog);
        model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
        return "transactionsEdit";
    }

    @PostMapping(value = {"/transactions/{transactionId}/edit"})
    public String updateTransaction(Model model,
                                @PathVariable long transactionId,
                                @ModelAttribute("transaction") TransactionLog transaction) {
        try {
            transaction.setId(transactionId);
            transactionLogService.update(transaction);
            return "redirect:/transactions/" + transaction.getId();
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
            return "transactionsEdit";
        }
    }
}
