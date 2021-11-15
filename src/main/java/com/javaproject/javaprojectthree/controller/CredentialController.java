package com.javaproject.javaprojectthree.controller;

import com.javaproject.javaprojectthree.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private CredentialService credentialService;

    @Autowired
    public void setCredentialService(CredentialService credentialService){this.credentialService = credentialService;}


}
