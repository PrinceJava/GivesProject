package com.javaproject.javaprojectthree.controller;


import com.javaproject.javaprojectthree.JavaProjectThreeApplication;
import com.javaproject.javaprojectthree.service.PaymentService;
import com.javaproject.javaprojectthree.service.TransactionLogService;
import com.javaproject.javaprojectthree.service.impl.PaymentServiceImpl;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Tag(name = "Payment", description = "Endpoints for the previously jsp files")
public class PaymentController {

    @Autowired
    TransactionLogService transactionLogService;

    @GetMapping("/review")
    public String reviewPayment(Model model,
                                @RequestParam(name = "paymentId") String paymentId,
                                @RequestParam(name = "PayerID") String PayerID) throws PayPalRESTException {
//        PaymentService paymentService = new PaymentServiceImpl();
//        Payment payment = paymentService.getPaymentDetails(paymentId);
//
//        PayerInfo payerInfo = payment.getPayer().getPayerInfo();
//        Transaction transaction = payment.getTransactions().get(0);
//        ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();
        model.addAttribute("paymentId", paymentId);
        model.addAttribute("PayerID", PayerID);
        model.addAttribute("payer", JavaProjectThreeApplication.payerInfo);
        model.addAttribute("transaction",JavaProjectThreeApplication.transaction);
        model.addAttribute("shippingAddress", JavaProjectThreeApplication.shippingAddress);
        return "review";
    }

    @PostMapping("/receipt")
    public String paymentReceipt(Model model){

        String paymentId = JavaProjectThreeApplication.paymentId;
        String payerId = JavaProjectThreeApplication.payerId;
        try {
            PaymentService paymentService = new PaymentServiceImpl();
            Payment payment = paymentService.executePayment(paymentId, payerId);
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);

            String sender;
            if(JavaProjectThreeApplication.myUserDetails == null) sender = "Anonymous";
            else {
                sender = JavaProjectThreeApplication.myUserDetails.getUsername();
            }
            transactionLogService.createTransaction(
                    sender.toLowerCase(),
                    JavaProjectThreeApplication.charity.getTitle(),
                    Double.parseDouble(transaction.getAmount().getTotal()),
                    transaction.getDescription()
            );
            model.addAttribute("paymentId", paymentId);
            model.addAttribute("payerId", payerId);
            model.addAttribute("payer", payerInfo);
            model.addAttribute("transaction", transaction);

        }catch(Exception ignored){}
        return "receipt";
    }
}
