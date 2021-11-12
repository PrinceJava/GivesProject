package com.javaproject.javaprojectthree.service.impl;

import com.javaproject.javaprojectthree.model.Role;
import com.javaproject.javaprojectthree.model.User;
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

    @Autowired
    public void setUserRepository(UserRepository userRepository){this.userRepository = userRepository;}

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository){this.roleRepository = roleRepository;}

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

    // Database initialize logic goes here
}
