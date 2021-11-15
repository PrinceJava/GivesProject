package com.javaproject.javaprojectthree.service.impl;

import com.javaproject.javaprojectthree.exception.InformationExistException;
import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.Role;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.repository.CharityRepository;
import com.javaproject.javaprojectthree.repository.RoleRepository;
import com.javaproject.javaprojectthree.repository.UserRepository;
import com.javaproject.javaprojectthree.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class InitServiceImpl implements InitService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    CharityRepository charityRepository;

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
    public void addUserToCharity(String username, Long charityId) {
        System.out.println("Calling INIT SERVICE addRoleToUser ==>");
        User user = userRepository.findUserByUserName(username);
        Charity charity = charityRepository.findCharityById(charityId);
        charity.setUser(user);
        user.getCharityList().add(charity);
        user.setRoles(user.getRoles());
    }

    // Database initialize logic goes here
}
