package com.boyd.UserAuth.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.boyd.UserAuth.User;
import com.boyd.UserAuth.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Endpoint to authenticate a user.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return ResponseEntity with the authentication status.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestParam String username, @RequestParam String password) {
        try {
            boolean isAuthenticated = userService.authenticateUser(username, password);
            if (isAuthenticated) {
                return ResponseEntity.ok("User authenticated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during authentication.");
        }
    }

    /**
     * Endpoint to register a new user.
     *
     * @param user The user information to be registered.
     * @return ResponseEntity with the registration status.
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            boolean isRegistered = userService.registerUser(user);
            if (isRegistered) {
                return ResponseEntity.ok("User registered successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    }
}
