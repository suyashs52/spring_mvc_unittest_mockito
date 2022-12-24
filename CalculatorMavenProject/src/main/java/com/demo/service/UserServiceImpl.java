package com.demo.service;

import com.demo.data.UserRepository;
import com.demo.data.UserRepositoryImpl;
import com.demo.model.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {


    UserRepository userRepository;
    EmailVerificationService emailVerificationService;

    public UserServiceImpl(){

    }
    public UserServiceImpl(UserRepository userRepository,EmailVerificationService emailVerificationService) {
        this.userRepository = userRepository;
        this.emailVerificationService=emailVerificationService;
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String repeatPass) {
        if (firstName==null || firstName.trim().length()==0){
            throw new IllegalArgumentException("User's first name shouldn't be null");
        }
        User user=new User(firstName,lastName,email,password,repeatPass, UUID.randomUUID().toString());

        boolean result =false
        ;
        try{
            result=userRepository.save(user);
        }catch (RuntimeException ex){
            throw new UserServiceException(ex.getMessage());
        }

        if(result==false){
            throw new UserServiceException("Could not create users");
        }

        try{
            emailVerificationService.scheduleEmailConfirmation(user);
        }catch (RuntimeException ex){
            throw new UserServiceException(ex.getMessage());
        }
        return user;
    }
}
