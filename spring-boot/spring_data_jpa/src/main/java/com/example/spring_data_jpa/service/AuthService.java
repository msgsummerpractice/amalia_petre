package com.example.spring_data_jpa.service;
import com.example.spring_data_jpa.model.SignInRequest;
import com.example.spring_data_jpa.model.SignUpRequest;

public interface AuthService {
    String login(SignInRequest loginDto);
    String register(SignUpRequest registerDto);
}
