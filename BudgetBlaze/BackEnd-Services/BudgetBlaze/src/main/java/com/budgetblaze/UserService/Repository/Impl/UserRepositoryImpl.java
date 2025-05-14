package com.budgetblaze.UserService.Repository.Impl;

import com.budgetblaze.UserService.Model.User;
import com.budgetblaze.UserService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class UserRepositoryImpl {

    @Autowired
    UserRepository userRepository;

    public UserRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findUserByemail(String email)
    {

        List<User> users =userRepository.findAll();
        List<User> userwithEmail =new ArrayList<>();
        for(User user: users){
            if(user.getEmail().equals(email)){
                userwithEmail.add(user);
                return user;
            }
        }
        return null;


    }


    public User findUserBycontactDetails(String phone){

        List<User> users =userRepository.findAll();
        List<User> userWithPhone =new ArrayList<>();
        for(User user: users){
            if(user.getContactDetails().equals(phone)){
                userWithPhone.add(user);
                return user;
            }
        }
        return null;

    }





}
