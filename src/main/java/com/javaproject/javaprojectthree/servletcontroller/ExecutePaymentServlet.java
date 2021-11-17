package com.javaproject.javaprojectthree.servletcontroller;

import com.javaproject.javaprojectthree.JavaProjectThreeApplication;
import com.javaproject.javaprojectthree.controller.TransactionLogController;
import com.javaproject.javaprojectthree.model.TransactionLog;
import com.javaproject.javaprojectthree.service.TransactionLogService;
import com.javaproject.javaprojectthree.service.impl.PaymentServiceImpl;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import com.javaproject.javaprojectthree.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "execute_payment", value = "/execute_payment")
public class ExecutePaymentServlet extends HttpServlet {

    @Autowired
    TransactionLogService transactionLogService;

    public ExecutePaymentServlet(){}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerID");

        try {

            PaymentService paymentService = new PaymentServiceImpl();
            Payment payment = paymentService.executePayment(paymentId, payerId);

            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);

            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);

            String sender;
            if(JavaProjectThreeApplication.myUserDetails == null) sender = "Anonymous";
            else {
                sender = JavaProjectThreeApplication.myUserDetails.getUsername();
            }
            transactionLogService.createTransaction(
                    sender,
                    JavaProjectThreeApplication.charity.getTitle(),
                    Double.parseDouble(transaction.getAmount().getTotal()),
                    "comment"
                    );
            // call another post to update database for transaction log with completed and pending
            request.getRequestDispatcher("receipt.jsp").forward(request, response);

        }catch(PayPalRESTException ex){
            ex.printStackTrace();
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }

    }
}
