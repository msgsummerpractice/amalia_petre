package com.example.spring_data_jpa.service;
import com.example.spring_data_jpa.model.SignInRequest;

public interface AuthService {
    String login(SignInRequest loginDto);
}
