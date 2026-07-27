package com.example.spring_data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.spring_data_jpa.model.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findTop10ByUsernameLikeIgnoreCaseOrderByUsernameAsc(String username);
    public User findByEmail(String email);
    public User findByUsername(String username);

    @Query("SELECT COUNT(*) FROM User")
    public int countUsers();
}
