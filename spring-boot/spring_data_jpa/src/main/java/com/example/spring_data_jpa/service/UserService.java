package com.example.spring_data_jpa.service;

import java.util.List;
import com.example.spring_data_jpa.model.User;
import org.springframework.stereotype.Service;
import com.example.spring_data_jpa.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // Create a new user
    public User createUser(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return userRepository.save(user);
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Retrieve a user by ID
    public User getUserById(Integer id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + id);
        }
        if(!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        return userRepository.findById(id).orElse(null);
    }

    // Retrieve a user by email
    public User getUserByEmail(String email) {
        if(email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        return user;
    }

    // Retrieve a user by username
    public User getUserByUsername(String username) {
        if(username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new IllegalArgumentException("User not found with username: " + username);
        }
        return user;
    }

    // Update an existing user
    public User updateUser(Integer id, User user) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + id);
        }
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        user.setId(id);
        return userRepository.save(user);
    }

    // Delete a user by ID
    public void deleteUser(Integer id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + id);
        }
        if(!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    // Search top 10 users by username
    public List<User> getTop10UsersByUsername(String username) {
        if(username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        return userRepository.findTop10ByUsernameLikeIgnoreCaseOrderByUsernameAsc("%" + username + "%");
    }

    // Count total number of users
    public int countUsers() {
        return userRepository.countUsers();
    }
}
