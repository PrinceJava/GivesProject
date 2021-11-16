package com.javaproject.javaprojectthree.servletcontroller;

import com.javaproject.javaprojectthree.controller.AuthenticationController;
import com.javaproject.javaprojectthree.controller.CharityController;
import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.TransactionLog;
import com.javaproject.javaprojectthree.model.forms.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "register_charity", value = "/register_charity")
public class RegisterCharityServlet extends HttpServlet {

    public RegisterCharityServlet(){}

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    CharityController charityController;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        double goal = Double.parseDouble(request.getParameter("amount"));
        double totalReceived = 0;
        String pictureURL = request.getParameter("pictureURL");

        Charity charity = new Charity(title,description,goal,totalReceived,false,pictureURL);

        try {
            String approvalLink = charityController.registerCharity(charity);
            response.sendRedirect(approvalLink);
        }catch(Exception ignored){
        }
    }
}
