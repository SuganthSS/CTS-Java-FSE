package com.cognizant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cognizant.service.UserService;

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    public UserController() {
    }
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    public String getUser(int userId) {
        return userService.getUserById(userId);
    }
    
    public List<String> listUsers() {
        return userService.getAllUsers();
    }
    
    public String createUser(String name) {
        boolean isSuccess = userService.saveUser(name);
        return isSuccess ? "Great, the user was created" : "Oops, couldn't create the user";
    }
    
    public String deleteUser(int userId) {
        boolean isSuccess = userService.deleteUser(userId);
        return isSuccess ? "Done, the user was removed" : "Sorry, couldn't remove the user";
    }
}
