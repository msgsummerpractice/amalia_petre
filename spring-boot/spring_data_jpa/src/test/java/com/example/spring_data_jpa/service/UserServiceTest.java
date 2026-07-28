package com.example.spring_data_jpa.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.spring_data_jpa.model.Role;
import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.model.UserRequest;
import com.example.spring_data_jpa.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private User sampleUser;
    private UserRequest sampleUserRequest;
    private List<User> sampleUserList;
    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");

        sampleUser = new User();
        sampleUser.setId(1);
        sampleUser.setUsername("testuser");
        sampleUser.setEmail("email@example.com");
        sampleUser.setPassword("password");
        sampleUser.setFirstname("Test");
        sampleUser.setLastname("User");
        sampleUser.setRole(role);

        sampleUserList = List.of(sampleUser,
                new User(2, "anotheruser", "another@example.com", "password", "Another", "User", role),
                new User(3, "thirduser", "third@example.com", "password", "Third", "User", role));

        sampleUserRequest = new UserRequest();
        sampleUserRequest.setUsername("testuser");
        sampleUserRequest.setEmail("email@example.com");
        sampleUserRequest.setPassword("password");
        sampleUserRequest.setFirstname("Test");
        sampleUserRequest.setLastname("User");
    }

    @Test
    @DisplayName("Test createUser method")
    @Disabled
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        User createdUser = userService.createUser(sampleUser);
        assertNotNull(createdUser);
        assertEquals(sampleUser.getUsername(), createdUser.getUsername());

    }

    @Test
    @DisplayName("Test getAllUsers method")@Disabled
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(sampleUser));
        Page<User> usersPage = userService.getAllUsers(Pageable.unpaged());
        List<User> users = usersPage.getContent();
        //List<User> users = userService.getAllUsers();
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(sampleUser.getUsername(), users.get(0).getUsername());
    }

    @Test
    @DisplayName("Test getUserById method")@Disabled
    void testGetUserById() {
        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(sampleUser));
        //when(userRepository.existsById(1)).thenReturn(true);

        User user = userService.getUserById(1);
        assertNotNull(user);
        assertEquals(sampleUser.getUsername(), user.getUsername());
    }

    @Test
    @DisplayName("Test getUserByEmail method")@Disabled
    void testGetUserByEmail() {
        when(userRepository.findByEmail("email@example.com")).thenReturn(sampleUser);

        User user = userService.getUserByEmail("email@example.com");
        assertNotNull(user);
        assertEquals(sampleUser.getUsername(), user.getUsername());
    }

    @Test
    @DisplayName("Test getUserByUsername method")@Disabled
    void testGetUserByUsername() {
        when(userRepository.findByUsername("testuser")).thenReturn(sampleUser);

        User user = userService.getUserByUsername("testuser");
        assertNotNull(user);
        assertEquals(sampleUser.getEmail(), user.getEmail());
    }

    @Test
    @DisplayName("Test updateUser method")@Disabled
    void testUpdateUser() {
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);
        User updatedUser = userService.updateUser(1, sampleUser);
        assertNotNull(updatedUser);
        assertEquals(sampleUser.getUsername(), updatedUser.getUsername());
    }

    @Test
    @DisplayName("Test deleteUser method")@Disabled
    void testDeleteUser() {
        when(userRepository.existsById(1)).thenReturn(true);
        userService.deleteUser(1);

        assertDoesNotThrow(() -> userService.deleteUser(1));
    }

    @Test
    @DisplayName("Test getTop10UsersByUsername method")@Disabled
    void testGetTop10UsersByUsername() {
        when(userRepository.findTop10ByUsernameLikeIgnoreCaseOrderByUsernameAsc("%testuser%"))
                .thenReturn(sampleUserList);

        List<User> users = userService.getTop10UsersByUsername("testuser");
        assertNotNull(users);
        assertEquals(3, users.size());
        assertEquals(sampleUser.getUsername(), users.get(0).getUsername());
    }

    @Test
    @DisplayName("Test countUsers method")@Disabled
    void testCountUsers() {
        when(userRepository.countUsers()).thenReturn(3);
        int count = userService.countUsers();
        assertEquals(3, count);
    }

    // ----------------------- INVALID TEST CASES -----------------------

    @Test
    @DisplayName("Test createUser_Invalid method")@Disabled
    void testCreateUser_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(null));
    }

    @Test
    @DisplayName("Test getAllUsers_Invalid method")@Disabled
    void testGetAllUsers_Invalid() {
        when(userRepository.findAll()).thenReturn(List.of());
        Page<User> usersPage = userService.getAllUsers(Pageable.unpaged());
        List<User> users = usersPage.getContent();
        //List<User> users = userService.getAllUsers();
        assertNotNull(users);
        assertEquals(0, users.size());
    }

    @Test
    @DisplayName("Test getUserById_Invalid method")@Disabled
    void testGetUserById_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(-1));
    }

    @Test
    @DisplayName("Test getUserByEmail_Invalid method")@Disabled
    void testGetUserByEmail_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> userService.getUserByEmail(null));
    }

    @Test
    @DisplayName("Test getUserByUsername_Invalid method")@Disabled
    void testGetUserByUsername_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> userService.getUserByUsername(null));
    }

    @Test
    @DisplayName("Test updateUser_Invalid method")@Disabled
    void testUpdateUser_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(-1, null));
    }

    @Test
    @DisplayName("Test deleteUser_Invalid method")@Disabled
    void testDeleteUser_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(-1));
    }

    @Test
    @DisplayName("Test getTop10UsersByUsername method")@Disabled
    void testGetTop10UsersByUsername_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> userService.getTop10UsersByUsername(""));
    }

}
