package com.example.testapp.converter;

import com.example.testapp.entity.User;
import com.example.testapp.dto.UserDto;

public class UserConverter {
    public static UserDto entityToUser(User entity){
        return new UserDto(entity.getId(), entity.getEmail(), entity.getName());
    }

    public static User userToEntity(UserDto user, String password, String salt, String token, String code, boolean isValid){
        User entity = new User();
        entity.setId(user.getId());
        entity.setEmail(user.getEmail());
        entity.setPassToken(token);
        entity.setPassword(password);
        entity.setSalt(salt);
        entity.setIsValid(isValid);
        entity.setValidationCode(code);
        return entity;
    }
}
