package com.javaproject.javaprojectthree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class JavaProjectThreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaProjectThreeApplication.class, args);
    }

}
