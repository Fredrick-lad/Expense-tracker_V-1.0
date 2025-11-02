package com.daily_expense_tracker.controller;

import com.daily_expense_tracker.model.User;
import com.daily_expense_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

        // ==================== REGISTRATION ====================
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        // ResponseEntity<?> means "response can contain any type"
        // Used when we might return different types (User on success, error message on failure)
        
        // Check if username already exists
        if (userService.existsByUsername(user.getUsername())){
            return ResponseEntity.badRequest()
                                .body("Username already exists");
        }
        
        // Check if email already exists
        Optional<User> existingEmail = userService.findByEmail(user.getEmail());
        if (existingEmail.isPresent()) {
            return ResponseEntity.badRequest()
                                .body("Email already registered");
        }


        // Save the new user
        // TODO: In production, hash password with BCrypt before saving
        User savedUser = userService.registerUser(user);

        // Return 201 CREATED with the user
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

        // ==================== LOGIN ====================
    
        @PostMapping("login")
        public ResponseEntity<?> login(@RequestBody User loginRequest){
        // Validate the credentiials
        Optional<User> user = userService.ValidateLogin(loginRequest.getUsername(), loginRequest.getPassword());

        if (user.isPresent()){

            return ResponseEntity.ok(user.get());
        } else {
            // Incase of a log in faiure due to wrong credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("INvalid username or password");
        }
    }
        // ==================== GET USER INFO ====================
        
        // Get user by id
        @GetMapping("/{id}")
        public ResponseEntity<User> getUserById(@PathVariable Long id){
            Optional<User> user = userService.getUserById(id);

            return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }
        
        // Get user by username
        @GetMapping("/username/{username}")
        public ResponseEntity<User> getUserByUserName(@PathVariable String username){
            Optional<User> user = userService.findByUsername(username);

            return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

        // Check if username is available
        @GetMapping("/check-username")
        public ResponseEntity<Boolean> checkUserNameAvailability(@RequestParam String username){
            boolean available = !userService.existsByUsername(username);

            return ResponseEntity.ok(available);
        }
}

