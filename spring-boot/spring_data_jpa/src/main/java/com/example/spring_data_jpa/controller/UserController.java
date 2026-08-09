package com.example.spring_data_jpa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import jakarta.validation.Valid;
import org.springframework.data.web.PageableDefault;
import com.example.spring_data_jpa.model.UserRequest;
import com.example.spring_data_jpa.model.UserResponse;
import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello-world")
    public String getHello() {
        return "Hello, World!";
    }
    

   // ----------------------------------- HTTP METHODS USING DTOs -----------------------------------------------------
    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            //MediaType.APPLICATION_XML_VALUE
    })
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<UserResponse>> getUsers(@PageableDefault(size = 7) Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsersDTO(pageable));
    }

    

    // POST - merge
    // IN BRUNO NU PUNE ID
    // Added validation for the POST method using DTOs
    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            //MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            //MediaType.APPLICATION_XML_VALUE
    })
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public UserResponse createUserDTO(@Valid @RequestBody UserRequest userRequest,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader) {

        if (userRequest.getEmail() == null || userRequest.getEmail().trim().isEmpty()
                || userRequest.getUsername() == null
                || userRequest.getUsername().trim().isEmpty()) {

            throw new IllegalArgumentException("Email and username cannot be null or empty");

        } else if (userRequest.getEmail().length() > 50 || userRequest.getUsername().length() > 50) {

            throw new IllegalArgumentException("Email and username must not exceed 50 characters");

        } else if (!userRequest.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new IllegalArgumentException("Invalid email format");

        } else if (userRequest.getFirstname() == null || userRequest.getFirstname().trim().isEmpty()
                || userRequest.getLastname() == null
                || userRequest.getLastname().trim().isEmpty()) {

            throw new IllegalArgumentException("Firstname and lastname cannot be null or empty");

        } else if (userRequest.getFirstname().length() > 50 || userRequest.getLastname().length() > 50) {

            throw new IllegalArgumentException("Firstname and lastname must not exceed 50 characters");

        } else {

            UserResponse createdUser = userService.createUserFromDTO(userRequest);
            if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
                return createdUser;
            } else if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
                return createdUser;
            } else {
                throw new UnsupportedOperationException("Unsupported media type: " + acceptHeader);
            }
        }
    }

    // GET - merge
    @GetMapping(value = "/id/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            //MediaType.APPLICATION_XML_VALUE
    })
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public UserResponse getUserByIdDTO(@PathVariable Integer id,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader) {
        UserResponse user = userService.getUserByIdFromDTO(id);
        return ResponseEntity.ok().body(user).getBody();
    }

    // PUT - merge
    // Added validation for the PUT method using DTOs
    // Because we are using DTOs, the UserRequest already has validation, so the one here is redundat and won't do anything, 
    // but I left it here just in case
    @PutMapping(value = "/put/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
    })
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> updateUserDTO(@PathVariable Integer id, @RequestBody UserRequest userRequest,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader) {
        
        if (userRequest.getEmail() == null || userRequest.getEmail().trim().isEmpty() || userRequest.getUsername() == null
                || userRequest.getUsername().trim().isEmpty()) {

            throw new IllegalArgumentException("Email and username cannot be null or empty");

        } else if (userRequest.getEmail().length() > 50 || userRequest.getUsername().length() > 50) {

            throw new IllegalArgumentException("Email and username must not exceed 50 characters");

        } else if (!userRequest.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new IllegalArgumentException("Invalid email format");

        } else {

            UserResponse updatedUser = userService.updateUserFromDTO(id, userRequest);
            return ResponseEntity.ok().body(updatedUser);
        }
    }

    // DELETE - merge
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUserDTO(@PathVariable Integer id) {
        userService.deleteUserFromDTO(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH - nu am mai testat
    @PatchMapping(value = "/patch/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
    })
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> partialUpdateUserDTO(@PathVariable Integer id,
            @RequestBody UserRequest userRequest,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader) {

        UserResponse updatedUser = userService.partialUpdateUserFromDTO(id, userRequest);
        return ResponseEntity.ok().body(updatedUser);
    }
}
