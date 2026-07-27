package com.example.spring_data_jpa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import jakarta.validation.Valid;
import com.example.spring_data_jpa.model.UserRequest;
import com.example.spring_data_jpa.model.UserResponse;
import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users= userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        User updatedUser=userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> partialUpdateUser(@PathVariable Integer id, @RequestBody User user) {
        User updatedUser = userService.partialUpdateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    // ----------------------------------- HTTP METHODS USING DTOs -----------------------------------

    // POST
    @PostMapping("/dto")
    public ResponseEntity<UserResponse> createUserDTO(@Valid @RequestBody UserRequest userRequest) {
        UserResponse createdUser = userService.createUserFromDTO(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // GET
    @GetMapping("/dto/{id}")
    public ResponseEntity<UserResponse> getUserDTO(@PathVariable Integer id) {
        UserResponse user = userService.getUserByIdFromDTO(id);
        return ResponseEntity.ok(user);
    }

    // PUT
    @PutMapping("/dto/{id}")
    public ResponseEntity<UserResponse> updateUserDTO(@PathVariable Integer id, @RequestBody UserRequest userRequest) {
        UserResponse updatedUser=userService.updateUserFromDTO(id, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    // DELETE
    @DeleteMapping("/dto/{id}")
    public ResponseEntity<Void> deleteUserDTO(@PathVariable Integer id) {
        userService.deleteUserFromDTO(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH
    @PatchMapping("/dto/{id}")
    public ResponseEntity<UserResponse> partialUpdateUserDTO(@PathVariable Integer id, @RequestBody UserRequest userRequest) {
        UserResponse updatedUser = userService.partialUpdateUserFromDTO(id, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

}
