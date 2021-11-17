package com.javaproject.javaprojectthree.service.impl;

import com.javaproject.javaprojectthree.JavaProjectThreeApplication;
import com.javaproject.javaprojectthree.exception.InformationExistException;
import com.javaproject.javaprojectthree.exception.InformationNotFoundException;
import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.TransactionLog;
import com.javaproject.javaprojectthree.repository.CharityRepository;
import com.javaproject.javaprojectthree.repository.TransactionLogRepository;
import com.javaproject.javaprojectthree.service.TransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {

    @Autowired
    TransactionLogRepository transactionLogRepository;

    @Autowired
    CharityRepository charityRepository;

    @Override
    public TransactionLog createTransaction(String sender, String receiver, double amount, String comment) {
        TransactionLog newTransaction = new TransactionLog();
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setDate(LocalDate.now());
        newTransaction.setAmount(amount);
        newTransaction.setComment(comment);
        return transactionLogRepository.save(newTransaction);
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
        return transactionLogRepository.findTransactionLogById(id);
    }

    @Override
    public void deleteTransaction(TransactionLog transaction) {

    }

    @Override
    public Charity findCharityByReceiver(Long transactionId) {
        TransactionLog transactionLog = transactionLogRepository.findTransactionLogById(transactionId);
        Charity charity = charityRepository.findByTitle(transactionLog.getReceiver());
        return charity;
    }

    @Override
    public TransactionLog save(TransactionLog transactionLog) throws InformationNotFoundException, InformationExistException {
        if(JavaProjectThreeApplication.myUserDetails != null) {
            if (!StringUtils.isEmpty(transactionLog.getReceiver())) {
                if (transactionLog.getId() != null && transactionLogRepository.existsById(transactionLog.getId())) {
                    throw new InformationExistException("Transaction with id: " + transactionLog.getId() +
                            " already exists");
                }
                return transactionLogRepository.save(transactionLog);
            } else {
                throw new InformationNotFoundException("Failed to save contact");
            }
        }else {
            throw new InformationNotFoundException("Failed to save contact");
        }
    }

    @Override
    public void update(TransactionLog transactionLog)
                  throws InformationNotFoundException, InformationExistException {
            if (JavaProjectThreeApplication.myUserDetails != null) {
                if (!StringUtils.isEmpty(transactionLog.getReceiver())) {
                    if (!transactionLogRepository.existsById(transactionLog.getId())) {
                        throw new InformationNotFoundException("Cannot find Transaction with id: " + transactionLog.getId());
                    }
                    transactionLogRepository.save(transactionLog);
                } else {
                    throw new InformationNotFoundException("Failed to save transaction");
                }
            } else {
                throw new InformationNotFoundException("Must be logged in to perform this action");
            }
    }
}
