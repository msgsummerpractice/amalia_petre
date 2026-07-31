package com.example.spring_data_jpa.model;
import com.example.spring_data_jpa.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    private String username;
    private String password;
    // added role field to SignInRequest class
    private Role role;
}
