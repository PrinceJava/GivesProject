package com.javaproject.javaprojectthree.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Credential findCredentialById(Long credentialId);

}
