package com.javaproject.javaprojectthree.service;

import com.javaproject.javaprojectthree.model.TransactionLog;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.model.forms.LoginRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface TransactionLogService {
    TransactionLog createTransaction(String sender, String receiver, LocalDate date, double amount, String comment);
    List<TransactionLog> findAllTransactions();
    List<TransactionLog> findAllTransactionsByReceiver(String receiver);
    TransactionLog findTransactionById(Long id);
    TransactionLog updateTransaction(TransactionLog transaction);
    void deleteTransaction(TransactionLog transaction);
}
