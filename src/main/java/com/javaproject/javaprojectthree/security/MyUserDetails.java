package com.javaproject.javaprojectthree.security;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


/*
* Provides core user information.
Implementations are not used directly by Spring Security for security purposes.
* They simply store user information which is later encapsulated into Authentication objects.
* This allows non-security related user information (such as email addresses, telephone numbers etc)
*       to be stored in a convenient location.

Concrete implementations must take particular care to ensure the non-null contract detailed for each method is enforced.
* See User for a reference implementation (which you might like to extend or use in your code).
*
* https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetails.html

* */
public class MyUserDetails implements UserDetails {

    private User user;
    private String userName;
    private String password;
    private String emailAddress;

    // LOOK INTO GRANTED AUTHORITIES

    // CONSTRUCTOR THAT THE myUserDetailsService Returns from the UserService Interface Method
    public MyUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    // -------------------------------- USER DETAILS INTERFACE ------------------------------------------//
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }



    // CAUSED A LOT OF ERRORS IF YOU DON"T HAVE
    // http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    // SET UP IN SECURITY CONFIGURER
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmailAddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}