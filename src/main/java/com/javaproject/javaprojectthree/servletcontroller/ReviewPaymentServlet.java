package com.javaproject.javaprojectthree.servletcontroller;

import com.javaproject.javaprojectthree.JavaProjectThreeApplication;
import com.javaproject.javaprojectthree.controller.PaymentController;
import com.javaproject.javaprojectthree.service.impl.PaymentServiceImpl;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import com.javaproject.javaprojectthree.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "review_payment", value = "/review_payment")
public class ReviewPaymentServlet extends HttpServlet {

    @Autowired
    PaymentController paymentController;


    public ReviewPaymentServlet(){}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerID");

        JavaProjectThreeApplication.paymentId = paymentId;
        JavaProjectThreeApplication.payerId = payerId;
        try {
            PaymentService paymentService = new PaymentServiceImpl();
            Payment payment = paymentService.getPaymentDetails(paymentId);

            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);
            ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();

            // THOUGHTS ARE TO CALL PAYMENT CONTROLLER
//            paymentController.reviewPayment(transaction, payerInfo, shippingAddress);

            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);
            request.setAttribute("shippingAddress", shippingAddress);

            JavaProjectThreeApplication.payerInfo = payerInfo;
            JavaProjectThreeApplication.transaction = transaction;
            JavaProjectThreeApplication.shippingAddress = shippingAddress;

            String url = "review?paymentId=" + paymentId + "&PayerID=" + payerId;
            request.getRequestDispatcher(url).forward(request, response);

        }catch(PayPalRESTException ex){
            ex.printStackTrace();
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }
}
