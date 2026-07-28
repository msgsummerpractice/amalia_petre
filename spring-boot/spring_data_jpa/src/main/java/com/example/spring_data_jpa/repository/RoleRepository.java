package com.example.spring_data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.spring_data_jpa.model.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    
}
