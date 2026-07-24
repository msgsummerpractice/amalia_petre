package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.example.demo.model.User;
import com.example.demo.repository.UsersRepo;
import org.springframework.stereotype.Service;

// Service class that adds bussiness logic to the repository layer
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UsersRepo usersRepo;

    @Override
    public List<User> getAllUsers() {
        List<User> users = usersRepo.findAll();
        if(users == null || users.isEmpty()) {
            throw new RuntimeException("No users found");
        }
        return users;
    }

    @Override
    public User getUserById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        User user = usersRepo.findById(id);
        if(user == null) {
            throw new RuntimeException("User not found whith ID: " + id);
        }
        return user;
    }

    @Override
    public void createUser(User user) {
        if(user == null || user.getName() == null || user.getAge() <= 0) {
            throw new IllegalArgumentException("Invalid user data");
        }
        usersRepo.save(user);
    }

}
