package com.example.testapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDataDto {
    private String email;
    private String name;
    private String password;
}
