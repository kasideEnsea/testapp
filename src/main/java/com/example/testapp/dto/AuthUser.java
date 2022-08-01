package com.example.testapp.dto;

import com.example.testapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthUser {
    private User user;
    private String tokenData;
}
