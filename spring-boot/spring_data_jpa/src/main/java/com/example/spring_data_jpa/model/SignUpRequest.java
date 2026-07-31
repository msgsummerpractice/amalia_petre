package com.example.spring_data_jpa.model;
import lombok.Data;
import com.example.spring_data_jpa.model.Role;
@Data
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    // added role field to SignUpRequest class
    private Role role;

}
        