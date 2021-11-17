package com.javaproject.javaprojectthree.service.impl;

import com.javaproject.javaprojectthree.model.TransactionLog;
import com.javaproject.javaprojectthree.repository.TransactionLogRepository;
import com.javaproject.javaprojectthree.service.TransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {

    @Autowired
    TransactionLogRepository transactionLogRepository;

    @Override
    public TransactionLog createTransaction(String sender, String receiver, LocalDate date, double amount, String comment) {
        return null;
    }

    @Override
    public List<TransactionLog> findAllTransactions() {
        return transactionLogRepository.findAll();
    }

    @Override
    public List<TransactionLog> findAllTransactionsByReceiver(String receiver) {
        return null;
    }

    @Override
    public TransactionLog findTransactionById(Long id) {
        return null;
    }

    @Override
    public TransactionLog updateTransaction(TransactionLog transaction) {
        return null;
    }

    @Override
    public void deleteTransaction(TransactionLog transaction) {

    }
}
