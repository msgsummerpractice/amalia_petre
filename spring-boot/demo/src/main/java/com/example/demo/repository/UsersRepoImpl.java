package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;
import com.example.demo.model.User;

//Repository class that works directly with the "database" layer
@Repository
public class UsersRepoImpl implements UsersRepo {
    // Database simulation with a list of users
    public List<User> users = new ArrayList<>(List.of(
            new User(1L, "John Doe", 26),
            new User(2L, "Jane Smith", 30),
            new User(3L, "Alice Johnson", 22)));

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(User user) {
        users.add(user);
    }
}
