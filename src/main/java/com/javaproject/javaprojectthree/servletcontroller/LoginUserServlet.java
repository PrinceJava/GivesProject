package com.javaproject.javaprojectthree.servletcontroller;

import com.javaproject.javaprojectthree.controller.AuthenticationController;
import com.javaproject.javaprojectthree.model.OrderDetail;
import com.javaproject.javaprojectthree.model.forms.LoginRequest;
import com.javaproject.javaprojectthree.service.impl.PaymentServiceImpl;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "login_user", value = "/login_user")
public class LoginUserServlet extends HttpServlet {

    public LoginUserServlet(){}

    @Autowired
    AuthenticationController authenticationController;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        LoginRequest loginRequest = new LoginRequest(email,password);

        try {
            authenticationController.loginUser(loginRequest);
            String approvalLink = "http://givesapp-env.eba-j53cbhw3.us-east-2.elasticbeanstalk.com/";
            response.sendRedirect(approvalLink);
        }catch(Exception ignored){
        }
    }
}
