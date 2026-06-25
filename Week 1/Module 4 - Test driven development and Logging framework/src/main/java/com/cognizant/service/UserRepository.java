package com.cognizant.service;

import java.util.List;

public interface UserRepository {
    
    String findById(int id);
    
    List<String> findAll();
    
    boolean save(String username);
    
    boolean delete(int id);
    
    int count();
}
