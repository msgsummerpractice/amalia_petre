package com.example.spring_data_jpa.model;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}
