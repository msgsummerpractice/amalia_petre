package com.example.spring_data_jpa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private long expiresInSeconds;
}
