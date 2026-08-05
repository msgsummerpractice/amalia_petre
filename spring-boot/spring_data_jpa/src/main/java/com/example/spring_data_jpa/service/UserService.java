package com.example.spring_data_jpa.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.model.UserMapper;
import com.example.spring_data_jpa.model.UserRequest;
import com.example.spring_data_jpa.model.UserResponse;

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

    // Retrieve all users - updated to support pagination
    
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // Retrieve a user by ID
    public User getUserById(Integer id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + id);
        }
        // Had to comment this part so that my controller can handle the exception and return a 404 status code instead of throwing an exception

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

    // Patch update of a user
    public User partialUpdateUser(Integer id, User user) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + id);
        }
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        if(!user.getUsername().isEmpty()) {
            existingUser.setUsername(user.getUsername());
        }
        if(!user.getEmail().isEmpty()) {
            existingUser.setEmail(user.getEmail());
        }
        if(!user.getPassword().isEmpty()){
            existingUser.setPassword(user.getPassword());
        }
        if(!user.getFirstname().isEmpty()){
            existingUser.setFirstname(user.getFirstname());
        }
        if(!user.getLastname().isEmpty()){
            existingUser.setLastname(user.getLastname());
        }
        return userRepository.save(existingUser);

    }

    // ------------------------------------ DTO METHODS -----------------------------------

    public Page<UserResponse> getAllUsersDTO(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserMapper::toUserResponse);
    }

    // Create a new user using DTO
    public UserResponse createUserFromDTO(UserRequest userRequest) {
        if(userRequest == null) {
            throw new IllegalArgumentException("UserRequest cannot be null");
        }

        // User user = new User();
        // user.setUsername(userRequest.getUsername());
        // user.setEmail(userRequest.getEmail());
        // user.setPassword(userRequest.getPassword());
        // user.setFirstname(userRequest.getFirstname());
        // user.setLastname(userRequest.getLastname());

        User user = UserMapper.toUserEntity(userRequest);
        return UserMapper.toUserResponse(userRepository.save(user));
    }

    // Update a user by ID using DTO
    public UserResponse updateUserFromDTO(Integer id, UserRequest userRequest) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + id);
        }
        if(userRequest == null) {
            throw new IllegalArgumentException("UserRequest cannot be null");
        }
        User userToUpdate = UserMapper.toUserEntity(userRequest);
        User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        existingUser.setUsername(userToUpdate.getUsername());
        existingUser.setEmail(userToUpdate.getEmail());
        existingUser.setPassword(userToUpdate.getPassword());
        existingUser.setFirstname(userToUpdate.getFirstname());
        existingUser.setLastname(userToUpdate.getLastname());

        return UserMapper.toUserResponse(userRepository.save(existingUser));
    }

    // Patch update of a user using DTO
    public UserResponse partialUpdateUserFromDTO(Integer id, UserRequest userRequest) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + id);
        }
        if(userRequest == null) {
            throw new IllegalArgumentException("UserRequest cannot be null");
        }
        User userToUpdate = UserMapper.toUserEntity(userRequest);
        User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        if(userToUpdate.getUsername() != null && !userToUpdate.getUsername().isEmpty()) {
            existingUser.setUsername(userToUpdate.getUsername());
        }
        if(userToUpdate.getEmail() != null && !userToUpdate.getEmail().isEmpty()) {
            existingUser.setEmail(userToUpdate.getEmail());
        }
        if(userToUpdate.getPassword() != null && !userToUpdate.getPassword().isEmpty()){
            existingUser.setPassword(userToUpdate.getPassword());
        }
        if(userToUpdate.getFirstname() != null && !userToUpdate.getFirstname().isEmpty()){
            existingUser.setFirstname(userToUpdate.getFirstname());
        }
        if(userToUpdate.getLastname() != null && !userToUpdate.getLastname().isEmpty()){
            existingUser.setLastname(userToUpdate.getLastname());
        }

        return UserMapper.toUserResponse(userRepository.save(existingUser));
    }

    // Delete a user by ID using DTO
    public void deleteUserFromDTO(Integer id) {
        deleteUser(id);
    }

    // Get a user by ID and return as UserResponse DTO
    public UserResponse getUserByIdFromDTO(Integer id) {
        return UserMapper.toUserResponse(getUserById(id));
    }

    public UserResponse getUserByEmailFromDTO(String email) {
        return UserMapper.toUserResponse(getUserByEmail(email));
    }
}
