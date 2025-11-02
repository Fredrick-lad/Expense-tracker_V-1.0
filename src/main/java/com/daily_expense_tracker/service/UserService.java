package com.daily_expense_tracker.service;

import com.daily_expense_tracker.model.User;
import com.daily_expense_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Register new user
    public User registerUser(User user){
        //add pasword hashing here later
        return userRepository.save(user);
    }
    // Find user by username (for log in)
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    // Check if username exists
    public Boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    // Find user by email
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    // Get user by ID
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    // Get all users
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // Delete user
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    // Validate user credentials (for login)simplified
    public Optional<User> ValidateLogin(String username, String password){
        Optional<User> userOpt = userRepository.findByUsername(username);

        if(userOpt.isPresent()){
            User user = userOpt.get();
            // In real application, use password hashing and comparison
            if(user.getPassword().equals(password)){
                return Optional.of(user);
        }
    }
    return Optional.empty();
    }
}
