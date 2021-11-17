package com.javaproject.javaprojectthree.repository;

import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

    TransactionLog findTransactionLogById(Long transactionId);
    boolean existsById(Long transactionId);
    List<TransactionLog> findAllByReceiver(String charityName);
}
