package com.example.spring_data_jpa.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
}
