package com.javaproject.javaprojectthree.service.impl;

import com.javaproject.javaprojectthree.JavaProjectThreeApplication;
import com.javaproject.javaprojectthree.exception.InformationExistException;
import com.javaproject.javaprojectthree.exception.InformationNotFoundException;
import com.javaproject.javaprojectthree.model.Charity;


import com.javaproject.javaprojectthree.model.Role;
import com.javaproject.javaprojectthree.model.TransactionLog;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.model.forms.RegisterForm;

import com.javaproject.javaprojectthree.repository.CharityRepository;
import com.javaproject.javaprojectthree.repository.TransactionLogRepository;
import com.javaproject.javaprojectthree.repository.UserRepository;
import com.javaproject.javaprojectthree.security.MyUserDetails;
import com.javaproject.javaprojectthree.service.CharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class CharityServiceImpl implements CharityService {

    @Autowired
    CharityRepository charityRepository;

    @Autowired
    TransactionLogRepository transactionLogRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Charity createCharity(String title, String description, double goal, double totalReceived, Boolean verified, String pictureURL) {
        if (JavaProjectThreeApplication.myUserDetails != null) {
            if (!charityRepository.existsByTitle(title)) {

                Charity newCharity = new Charity();
                newCharity.setTitle(title);
                newCharity.setDescription(description);
                newCharity.setGoal(goal);
                newCharity.setTotalReceived(totalReceived);
                newCharity.setVerified(false);
                newCharity.setPictureURL(pictureURL);
                return charityRepository.save(newCharity);
            } else {
                throw new InformationExistException("Charity with the name " +
                        title + " already exists");
            }
        } else {
            throw new InformationNotFoundException("Must be logged in to perform this action");
        }
    }
    @Override
    public ResponseEntity<?> createCharity(RegisterForm registerForm) {
        if(JavaProjectThreeApplication.myUserDetails != null) {
            if (!charityRepository.existsByTitle(registerForm.getTitle())) {

                Charity newCharity = new Charity();
                newCharity.setTitle(registerForm.getTitle());
                newCharity.setDescription(registerForm.getDescription());
                newCharity.setGoal(registerForm.getGoal());
                newCharity.setTotalReceived(0);
                newCharity.setVerified(false);
                newCharity.setPictureURL(registerForm.getPictureURL());
                newCharity.setUser(
                        userRepository.findUserByUserName(registerForm.getUsername())
                );
                return ResponseEntity.ok(charityRepository.save(newCharity));
            } else {
                throw new InformationExistException("Charity with the name " +
                        registerForm.getTitle() + " already exists");
            }
        }
        else {
            throw new InformationNotFoundException("Must be logged in to perform this action");
            }
    }

    @Override
    public List<Charity> findAllCharities(int pageNumber, int rowPerPage) {
        List<Charity> contacts = new ArrayList<>();
        Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage,
                Sort.by("id").ascending());
        charityRepository.findAll(sortedByIdAsc).forEach(contacts::add);
        return contacts;
    }
    public List<Charity> findAllCharities(){
        return charityRepository.findAll();
    }

    @Override
    public Charity findCharityById(Long id) {
        return charityRepository.findCharityById(id);
    }

    public void deleteCharity(Long charityId) {
        if (JavaProjectThreeApplication.myUserDetails != null) {
            System.out.println("Service calling deleteCharity ==>");
            Optional<Charity> charity = charityRepository.findById(charityId);
            if (charity.isPresent()) {
                charityRepository.deleteById(charityId);
            } else {
                throw new InformationNotFoundException("Charity with id " + charityId + " not found");
            }
        } else {
            throw new InformationNotFoundException("Must be logged in to perform this action");
        }
    }

    @Override
    public Charity deleteCharity(String title, String description, double goal, double totalReceived, Boolean verified, String pictureURL) {
        return null;
    }


    public Charity updateCharity(Long charityId, String title, double goal, double totalReceived, Boolean verified, String pictureURL) {
        System.out.println("Service is calling updateCharity ==>");
        Charity charity = charityRepository.findCharityById(charityId);
        try {
            Charity updateCharity = charityRepository.findCharityById(charityId);
            updateCharity.setTitle(charity.getTitle());
            updateCharity.setDescription(charity.getDescription());
            updateCharity.setGoal(charity.getGoal());
            updateCharity.setTotalReceived(charity.getTotalReceived());
            updateCharity.setVerified(charity.getVerified());
            updateCharity.setPictureURL(charity.getPictureURL());
            return charityRepository.save(charity);
        } catch (NoSuchElementException e) {
            throw new InformationNotFoundException("charity name of " + charity + " not found");
        }
    }


    @Override
    public List<TransactionLog> findAllTransactionsByCharityId(Long charityId) {
        Charity charity = charityRepository.findCharityById(charityId);
        return transactionLogRepository.findAllByReceiver(charity.getTitle());
    }

    @Override
    public Charity save(Charity charity) throws InformationNotFoundException, InformationExistException {
        if(JavaProjectThreeApplication.myUserDetails != null) {
            if (!StringUtils.isEmpty(charity.getTitle())) {
                if (charity.getId() != null && charityRepository.existsByTitle(charity.getTitle())) {
                    throw new InformationExistException("Charity with id: " + charity.getId() +
                            " already exists");
                }
                return charityRepository.save(charity);
            } else {
                throw new InformationNotFoundException("Failed to save contact");
            }
        }else {
            throw new InformationNotFoundException("Failed to save contact");
        }
    }

    @Override
    public void update(Charity charity)
            throws InformationNotFoundException, InformationExistException {
        if (JavaProjectThreeApplication.myUserDetails != null) {
            if (!StringUtils.isEmpty(charity.getTitle())) {
                if (!charityRepository.existsByTitle(charity.getTitle())) {
                    throw new InformationNotFoundException("Cannot find Contact with id: " + charity.getId());
                }
                charityRepository.save(charity);
            } else {
                throw new InformationNotFoundException("Failed to save contact");
            }
        } else {
            throw new InformationNotFoundException("Must be logged in to perform this action");
        }
    }

    @Override
    public Charity findById(long charityId) {
        return charityRepository.findCharityById(charityId);
    }

}
