package com.cognizant.service;

import java.util.List;

public class UserService {
    
    private UserRepository repoInstance;
    
    public UserService(UserRepository repoInstance) {
        this.repoInstance = repoInstance;
    }
    
    public String getUserById(int userId) {
        return repoInstance.findById(userId);
    }
    
    public List<String> getAllUsers() {
        return repoInstance.findAll();
    }
    
    public boolean saveUser(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return repoInstance.save(name);
    }
    
    public boolean deleteUser(int userId) {
        return repoInstance.delete(userId);
    }
    
    public int getUserCount() {
        return repoInstance.count();
    }
}
