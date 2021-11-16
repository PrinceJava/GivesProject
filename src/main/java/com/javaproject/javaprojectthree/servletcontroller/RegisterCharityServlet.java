package com.javaproject.javaprojectthree.servletcontroller;

import com.javaproject.javaprojectthree.controller.AuthenticationController;
import com.javaproject.javaprojectthree.model.forms.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "login_users", value = "/login_users")
public class RegisterCharityServlet extends HttpServlet {

    public RegisterCharityServlet(){}

    @Autowired
    AuthenticationController authenticationController;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("WE MADE IT");
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//
//        LoginRequest loginRequest = new LoginRequest(email,password);
//
//        try {
//            authenticationController.loginUser(loginRequest);
//            String approvalLink = "/";
//            response.sendRedirect(approvalLink);
//        }catch(Exception ignored){
//        }
    }
}
