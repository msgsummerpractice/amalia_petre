package com.example.spring_data_jpa.service;
import com.example.spring_data_jpa.model.OttVerifyRequest;
import com.example.spring_data_jpa.model.SignInRequest;
import com.example.spring_data_jpa.model.SignUpRequest;
import com.example.spring_data_jpa.model.SignInResponse;

public interface AuthService {
    SignInResponse login(SignInRequest loginDto);
    String register(SignUpRequest registerDto);
    SignInResponse verifyOtt(OttVerifyRequest verifyDto);
}
