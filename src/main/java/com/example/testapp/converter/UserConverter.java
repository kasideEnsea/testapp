package com.example.testapp.converter;

import com.example.testapp.dao.UserDao;
import com.example.testapp.entity.User;

public class UserConverter {
    public static User daoToUser(UserDao dao){
        return new User(dao.getId(), dao.getEmail(), dao.getName());
    }

    public static UserDao userToDao (User user, String password, String salt, String token, String code, boolean isValid){
        UserDao dao = new UserDao();
        dao.setId(user.getId());
        dao.setEmail(user.getEmail());
        dao.setPassToken(token);
        dao.setPassword(password);
        dao.setSalt(salt);
        dao.setIsValid(isValid);
        dao.setValidationCode(code);
        return dao;
    }
}
