package com.javaproject.javaprojectthree.service.impl;

import com.javaproject.javaprojectthree.JavaProjectThreeApplication;
import com.javaproject.javaprojectthree.exception.InformationExistException;
import com.javaproject.javaprojectthree.exception.InformationNotFoundException;
import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.Role;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.model.forms.LoginRequest;
import com.javaproject.javaprojectthree.model.forms.LoginResponse;
import com.javaproject.javaprojectthree.repository.RoleRepository;
import com.javaproject.javaprojectthree.repository.UserRepository;
import com.javaproject.javaprojectthree.security.jwt.JWTUtils;
import com.javaproject.javaprojectthree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JWTUtils jwtUtils;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public User createUser(String firstName, String lastName, String userName, String emailAddress, String password, String roleName) {
        System.out.println("service is calling createUser==>");
        // if user not exists by the email
        // then create the user in the db

        if (!userRepository.existsByEmailAddress(emailAddress)) {

            User newUser = new User();
            newUser.setFirstName(firstName);
            newUser.setLastname(lastName);
            newUser.setUserName(userName);
            newUser.setEmailAddress(emailAddress);
            newUser.setPassword(passwordEncoder.encode(password));
            Role role = roleRepository.findByName(roleName);
            newUser.getRoles().add(role);
            newUser.setRoles(newUser.getRoles());
            return userRepository.save(newUser);
//            return newUser;
        } else {
            throw new InformationExistException("user with the email address " +
                    emailAddress + " already exists");
        }
    }

        public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
            System.out.println("service calling loginUser ==>");
            authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            JavaProjectThreeApplication.myUserDetails = userDetails;
            final String JWT = jwtUtils.generateToken(userDetails);
            return ResponseEntity.ok(new LoginResponse(JWT));
        }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void logout() {
        JavaProjectThreeApplication.myUserDetails = null;
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
        }


    public User saveUser(User user) throws InformationNotFoundException, InformationExistException {
        if(JavaProjectThreeApplication.myUserDetails != null) {
            if (!StringUtils.isEmpty(user.getEmailAddress())) {
                if (user.getId() != null && userRepository.existsByEmailAddress(user.getEmailAddress())) {
                    throw new InformationExistException("User with id: " + user.getEmailAddress() +
                            " already exists");
                }
                return userRepository.save(user);
            } else {
                throw new InformationNotFoundException("Failed to save user");
            }
        }else {
            throw new InformationNotFoundException("Failed to save user");
        }
    }

    @Override
    public User showEditUser(User user) {
        return userRepository.findUserByEmailAddress(user.getEmailAddress());
    }


    // Implemented method to find user by passed String email Address.
        public User findUserByEmailAddress(String email) {
            return userRepository.findUserByEmailAddress(email);
        }
    }

