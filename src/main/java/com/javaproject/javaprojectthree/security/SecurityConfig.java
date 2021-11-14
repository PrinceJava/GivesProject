package com.javaproject.javaprojectthree.security;

import com.javaproject.javaprojectthree.security.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;

/*
DHRUBOS ALGORITHM REST API PROJECT
------- SECURITY CONFIG ---------

Main purpose of this Configurer is to extend WebSecurityConfigurerAdapter class and take on it's overridden methods
    1. configure(HttpSecurity)
    2. configure(AuthenticationManager)
    BEANS TO START ON APP BOOT - PasswordEncoder encode - AuthenticationManager - UserDetails myUserDetails

Goal of this page is to
1. Call MyUserDetails, which implements UserDetails to find the user based off passed information, and sets variable including
    Granted Authorities.  This information will then be passed to JWTUtils for generateToken method to create the JWT TOKEN
2. Set configure(HTTPSecurity http) to include antMatchers for any (public) and .access(attribute of Role Name)
    A. sets restrictions for users
    B. sets restrictions for admin
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private MyUserDetailsService myUserDetailsService;

    @Autowired
    public void setMyUserDetailsService(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }


    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(
                        "/api/register", "/api/login", "/", "/users/all").permitAll()
//                .antMatchers("/admin/**").access("hasRole('ROLE_RECEIVER')")
//                .antMatchers("/user/**").access("hasRole('ROLE_SENDER')")
//                .antMatchers("/user/**").access("hasRole('ROLE_RECEIVER')")
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // fetching data for user for authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public MyUserDetails myUserDetails() {
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
    }
}