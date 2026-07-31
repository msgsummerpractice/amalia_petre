package com.example.spring_data_jpa.configuration;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.repository.UserRepository;

import lombok.Data;

@Service
@Data
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        GrantedAuthority authority = user.getRole() != null ? new SimpleGrantedAuthority(user.getRole().getName()) : null;

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authority != null ? Set.of(authority) : Set.of() 
        );
    }


}
