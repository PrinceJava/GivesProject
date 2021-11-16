package com.javaproject.javaprojectthree.servletcontroller;

import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.service.CharityService;
import com.javaproject.javaprojectthree.service.impl.PaymentServiceImpl;
import com.paypal.base.rest.PayPalRESTException;
import com.javaproject.javaprojectthree.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "authorize_payment", value = "/authorize_payment")
public class AuthorizePaymentServlet extends HttpServlet {

    public AuthorizePaymentServlet(){}

    @Autowired
    CharityService charityService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String product = request.getParameter("product");
        String subtotal = request.getParameter("subtotal");
        String shipping = request.getParameter("shipping");
        String tax = request.getParameter("tax");
        String total = request.getParameter("total");

//        Charity charity = charityService.findCharityById(Long.valueOf(request.getParameter("charity")));

        OrderDetail orderDetail = new OrderDetail(product, subtotal, shipping, tax, total);

        try {
            PaymentServiceImpl paymentService = new PaymentServiceImpl();
            String approvalLink = paymentService.authorizePayment(orderDetail);

            response.sendRedirect(approvalLink);
        }catch(PayPalRESTException ex){
            ex.printStackTrace();
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }
}
