package com.javaproject.javaprojectthree.model.forms;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUser {
    private String firstName;
    private String lastName;
    private String userName;
    private String emailAddress;
    private String password;
    private String role;
}
