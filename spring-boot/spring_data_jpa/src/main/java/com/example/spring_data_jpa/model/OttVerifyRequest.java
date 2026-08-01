package com.example.spring_data_jpa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OttVerifyRequest {
    private String username;
    private String token; // PIN
}
