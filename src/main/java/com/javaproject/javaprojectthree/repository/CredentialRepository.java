package com.javaproject.javaprojectthree.repository;


import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Credential findCredentialById(Long credentialId);

}
