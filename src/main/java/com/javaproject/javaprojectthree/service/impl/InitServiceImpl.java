package com.javaproject.javaprojectthree.service.impl;


import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.Role;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.exception.InformationExistException;
import com.javaproject.javaprojectthree.model.*;
import com.javaproject.javaprojectthree.repository.CharityRepository;
import com.javaproject.javaprojectthree.repository.RoleRepository;
import com.javaproject.javaprojectthree.repository.TransactionLogRepository;
import com.javaproject.javaprojectthree.repository.UserRepository;
import com.javaproject.javaprojectthree.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class InitServiceImpl implements InitService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    CharityRepository charityRepository;
    TransactionLogRepository transactionLogRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setCharityRepository(CharityRepository charityRepository) {
        this.charityRepository = charityRepository;
    }

    @Autowired
    public void setTransactionLogRepository(TransactionLogRepository transactionLogRepository){this.transactionLogRepository=transactionLogRepository;}

    @Override
    public User addUser(User userObject) {
        System.out.println("Calling INIT SERVICE saveUser ==>");
        return userRepository.save(userObject);
    }

    @Override
    public Role addRole(Role roleObject) {
        System.out.println("Calling INIT SERVICE saveRole ==>");
        return roleRepository.save(roleObject);
    }

    @Override
    public void addUserRole(String username, String roleName) {
        System.out.println("Calling INIT SERVICE addRoleToUser ==>");
        User user = userRepository.findUserByUserName(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        user.setRoles(user.getRoles());
    }

    @Override
    public Charity createCharity(String title, String description, double goal, double totalReceived, Boolean verified, String pictureURL) {
        System.out.println("Calling INIT SERVICE createCharity ==>");
        Charity newCharity = new Charity();
        newCharity.setTitle(title);
        newCharity.setDescription(description);
        newCharity.setGoal(goal);
        newCharity.setTotalReceived(totalReceived);
        newCharity.setVerified(verified);
        newCharity.setPictureURL(pictureURL);
        return charityRepository.save(newCharity);
    }

    @Override
    public Charity createCharity(String title, String description, double goal, double totalReceived, Boolean verified, String pictureURL, String CLIENT_ID, String CLIENT_SECRET, String MODE) {
        System.out.println("Calling INIT SERVICE createCharity with Credentials ==>");
        Charity newCharity = new Charity();
        newCharity.setTitle(title);
        newCharity.setDescription(description);
        newCharity.setGoal(goal);
        newCharity.setTotalReceived(totalReceived);
        newCharity.setVerified(verified);
        newCharity.setPictureURL(pictureURL);
        newCharity.setCLIENT_ID(CLIENT_ID);
        newCharity.setCLIENT_SECRET(CLIENT_SECRET);
        newCharity.setMODE(MODE);
        return charityRepository.save(newCharity);
    }

    @Override
    public void addUserToCharity(String username, Long charityId) {
        System.out.println("Calling INIT SERVICE addRoleToUser ==>");
        User user = userRepository.findUserByUserName(username);
        Charity charity = charityRepository.findCharityById(charityId);
        charity.setUser(user);
        user.getCharityList().add(charity);
        user.setRoles(user.getRoles());
    }

    @Override
    public TransactionLog createTransactionLog(String sender, String receiver, LocalDate date, double amount, String comment) {
        System.out.println("Calling INIT SERVICE createTransaction ==>");
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setSender(sender);
        transactionLog.setReceiver(receiver);
        transactionLog.setDate(LocalDate.now());
        transactionLog.setAmount(amount);
        transactionLog.setComment(comment);
        return transactionLogRepository.save(transactionLog);
    }

    // Database initialize logic goes here
}
