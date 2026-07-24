package com.example.demo.repository;
import java.util.List;
import com.example.demo.model.User;

public interface UsersRepo {
    public List<User> findAll();
    public User findById(Long id);
    public void save(User user);
}
