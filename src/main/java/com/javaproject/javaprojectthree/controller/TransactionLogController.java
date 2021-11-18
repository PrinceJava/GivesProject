package com.javaproject.javaprojectthree.controller;

import com.javaproject.javaprojectthree.JavaProjectThreeApplication;
import com.javaproject.javaprojectthree.exception.InformationNotFoundException;
import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.TransactionLog;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.service.CharityService;
import com.javaproject.javaprojectthree.service.TransactionLogService;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@Tag(name = "Transaction", description = "Endpoints for transaction CRUD operations")
@RequestMapping("/transactions")
public class TransactionLogController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CharityService charityService;

    @Autowired
    UserService userService;

    @Autowired
    TransactionLogService transactionLogService;

    @Operation(summary = "Get list of all transaction",
            description = "User can get all Transactions. This end point redirects to transactions page, " +
                    "Otherwise no action.",
            tags = {"Transaction"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully deleted transaction",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TransactionLog.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid action or URL supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
    @GetMapping("/")
    public String findAllTransactions(Model model) {
        List<TransactionLog> transactions = transactionLogService.findAllTransactions();
          model.addAttribute("transactions",transactions);
          model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
        return "transactions";
    }

    @Operation(summary = "Get the selected transaction",
            description = "User can get the Transaction. This end point redirects to transaction page, " +
                    "Otherwise no action.",
            tags = {"Transaction"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully selected transaction",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TransactionLog.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid transaction supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
    @GetMapping("/{transactionId}")
    public String findCharityById(
            @PathVariable(value = "transactionId") Long transactionId,
            Model model){
        model.addAttribute("transaction",transactionLogService.findTransactionById(transactionId));
        model.addAttribute("charity", transactionLogService.findCharityByReceiver(transactionId));
        model.addAttribute("myUser", JavaProjectThreeApplication.myUserDetails);
        return "transaction";
    }

    @Operation(summary = "Get the add transaction form",
            description = "User can add a Transaction. This end point redirects to transactionEdit, " +
                    "Otherwise no action.",
            tags = {"Transaction"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully loaded transaction form",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TransactionLog.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid URL or format supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
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

    @Operation(summary = "Add the selected transaction",
            description = "User can add the Transaction. This end point redirects to transaction page, " +
                    "Otherwise no action.",
            tags = {"Transaction"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully added transaction",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TransactionLog.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid transaction supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
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

    @Operation(summary = "Get the Edit transaction form",
            description = "User can edit the Transaction via the transactionEdit form provided by this get mapping. This end point redirects to transactionEdit page, " +
                    "Otherwise no action.",
            tags = {"Transaction"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully deleted transaction",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TransactionLog.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid transaction supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
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

    @Operation(summary = "Edit the selected transaction",
            description = "User can edit the Transaction. This end point redirects to transaction page, " +
                    "Otherwise no action.",
            tags = {"Transaction"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully deleted transaction",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TransactionLog.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid transaction supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
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

    @Operation(summary = "Delete the selected transaction",
            description = "User can delete the Transaction. This end point redirects to list of transactions, " +
                    "Otherwise no action.",
            tags = {"Transaction"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully deleted transaction",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TransactionLog.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid transaction supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                    content = @Content)
    })
    @DeleteMapping("/{transactionId}/delete")
    public String deleteTransaction(
            @PathVariable(value = "transactionId") Long transactionId){
        transactionLogService.deleteTransaction(transactionId);
        return "transactions";
    }
}
