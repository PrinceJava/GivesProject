package com.javaproject.javaprojectthree.model.forms;

import lombok.Data;

@Data
public class RegisterForm {
    private String userName;
    private String emailAddress;
    private String password;
    private String role;
}