package com.javaproject.javaprojectthree.model.forms;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterForm {
    private String title;
    private String description;
    private double goal;
    private String pictureURL;
    private String username;
}