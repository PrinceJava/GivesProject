# paypal-java-capstone-project

###Raul and Matthew Project 3


##Getting Started
1. Create a new database called _**projectthree**_ in postgres and connect to it.
    a. \c projecthree

Welcome to the Giving App Project with Java Spring Boot

![img.png](images/img.png)


This project involved using a REST API for a donation database that allows users to view a list
of charities and allows them to select a charity they wish to donate to. The database has 
endpoints for users, which require proper authentication using JSON Web Token. 

In the Giving application you will be able to show your giving spirit by allowing you to donate
to worthy causes. A user is provided with a list of charities, which were verified a true non-profits
using a boolean logic. A user is able to become a sender and receiver based on selected roles.

A user is able to create a new charity with a fundraising goal and is able to
keep track of that goal as they receive payments. 


## Original Entity Relationship Diagram (ERD):

![img.png](images/imgErd1.png)

## Final Entity Relationship Diagram (ERD):

![img_17.png](images/img_17.png)

##POM Dependencies


##Endpoints



##System Tools Used

|  |  |
| --- | :--- |
| ![img_5.png](images/img_5.png) | Spring Boot 
| ![img_7.png](images/img_7.png) | Postman
|![img_8.png](images/img_8.png)  | Json Web Tokens
|![img_6.png](images/img_6.png)  | IntelliJ IDEA 17
|![img_9.png](images/img_9.png) | Postgres
|![img_10.png](images/img_10.png) | LucidApp
|![img_12.png](images/img_12.png) |Thymeleaf
|![img_14.png](images/img_14.png) |GitHub Branching

##User Stories

![img_16.png](images/img_16.png)

| | 
|:---|
|1.	As a user, I would like to view a list of charities.
|2.	As a user, I would like to view details of a specific charity.
|3.	As a user, I would like to donate to any charity I choose.
|4.	As a user, I would like to log in.
|5.	As a user, I would like to donate without login in.
|6.	As a user, I would like to use my PayPal account to donate.
|7.	As a user, I would like to create a charity.
|8.	As a user, I would like to delete my charity.
|9.	As a user, I would like to update my charity.
|10. As a user, I would like to set fundraising goal.
|11. As a user, I would like to keep track of a fundraising total.
|12. A user can have an admin role or user role. 

##Project Challenges

![img_4.png](images/img_4.png)

1. Using a single Github repository proved to be challenging; however, after some research we
    were able to 

2. The first coding challenge we faced was creating a user with login credentials using JWT. We realized 
   the error was in our coding as the JWTUtils did not have the correct parameters in our class regarding
   Secret Key. Once this was corrected, we were able to generate a JWT Token for an admin and a user. 

3. Another challenge that appeared occurred during the credentials set up as the user or admin were unable
   to create a charity due to the authentication process not passing through to the web application. This
   caused us to refactor the code and remove the credential classes. The credentials were then added to the
   charity class as a user or admin can be tied to each fundraising charity. 

4. During the creation of a new charity, the URL re-direct would not pass the method for this class. 


