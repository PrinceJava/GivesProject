package com.javaproject.javaprojectthree.service.impl;


import com.javaproject.javaprojectthree.model.Credential;
import com.javaproject.javaprojectthree.repository.CredentialRepository;
import com.javaproject.javaprojectthree.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialServiceImpl implements CredentialService {

    private CredentialService credentialService;

    @Autowired
    public void setCredentialRepository(CredentialRepository credentialRepository) {
    }

    @Override
    public Credential findCredentialById(Long id) {
        return findCredentialById(id);
    }
}
