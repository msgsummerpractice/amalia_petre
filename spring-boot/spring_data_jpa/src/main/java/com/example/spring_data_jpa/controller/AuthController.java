package com.example.spring_data_jpa.controller;

import com.example.spring_data_jpa.model.SignInResponse;
import com.example.spring_data_jpa.model.SignInRequest;
import com.example.spring_data_jpa.model.SignUpRequest;
import com.example.spring_data_jpa.model.OttVerifyRequest;
import com.example.spring_data_jpa.service.AuthService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<SignInResponse> login(@RequestBody SignInRequest loginDto){
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/verify-ott")
    public ResponseEntity<SignInResponse> verifyOtt(@RequestBody OttVerifyRequest verifyDto) {
        return ResponseEntity.ok(authService.verifyOtt(verifyDto));
    }
    

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignUpRequest registerDto){

        String response = authService.register(registerDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
