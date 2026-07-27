package com.example.spring_data_jpa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
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

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // For now, 404 status codes are returned only for the Get (By ID, By Email, By Username) methods, but not for the other methods
    // The exercise said: "Use appropriate HTTP status codes for success and 
    // error responses (e.g., 201 Created for successful POST, 404 Not Found for invalid IDs)."

    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<User> getUserById(@PathVariable Integer id,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(user);
        } else if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }
    }

    @GetMapping(value = "/{email}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<User> getUserByEmail(@PathVariable String email,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(user);
        } else if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }
    }

    @GetMapping(value = "/{username}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<User> getUserByUsername(@PathVariable String username,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(user);
        } else if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }
    }

    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<User> createUser(@RequestBody User user,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader) {
        User createdUser = userService.createUser(user);
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_XML).body(createdUser);
        } else if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(createdUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader) {
        User updatedUser = userService.updateUser(id, user);
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(updatedUser);
        } else if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<User> partialUpdateUser(@PathVariable Integer id, @RequestBody User user,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader) {
        User updatedUser = userService.partialUpdateUser(id, user);
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(updatedUser);
        } else if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }
    }

    // ----------------------------------- HTTP METHODS USING DTOs
    // -----------------------------------------------------

    // POST - merge
    // IN BRUNO NU PUNE ID
    // Nu merge cu XML, este o problema cu Jackson, nu stiu cum sa o rezolv, merge doar cu JSON
    @PostMapping(value = "/dto", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public UserResponse createUserDTO(@Valid @RequestBody UserRequest userRequest,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader) {
        UserResponse createdUser = userService.createUserFromDTO(userRequest);
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            return createdUser;
        } else if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
            return createdUser;
        } else {
            throw new UnsupportedOperationException("Unsupported media type: " + acceptHeader);
        }
    }

    // GET - merge
    @GetMapping(value = "/dto/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public UserResponse getUserDTO(@PathVariable Integer id,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader) {
        UserResponse user = userService.getUserByIdFromDTO(id);
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            return user;
        } else if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
            return user;
        } else {
            throw new UnsupportedOperationException("Unsupported media type: " + acceptHeader);
        }
    }

    // PUT - nu am mai testat
    @PutMapping(value = "/dto/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<UserResponse> updateUserDTO(@PathVariable Integer id, @RequestBody UserRequest userRequest,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader) {
        UserResponse updatedUser = userService.updateUserFromDTO(id, userRequest);
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(updatedUser);
        } else if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }
    }

    // DELETE - merge
    @DeleteMapping("/dto/{id}")
    public ResponseEntity<Void> deleteUserDTO(@PathVariable Integer id) {
        userService.deleteUserFromDTO(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH - nu am mai testat
    @PatchMapping(value = "/dto/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<UserResponse> partialUpdateUserDTO(@PathVariable Integer id,
            @RequestBody UserRequest userRequest,
            @RequestHeader(name = "Accept", defaultValue = "application/json") String acceptHeader) {

        UserResponse updatedUser = userService.partialUpdateUserFromDTO(id, userRequest);
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(updatedUser);
        } else if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }
    }
}
