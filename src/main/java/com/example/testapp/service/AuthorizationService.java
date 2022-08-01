package com.example.testapp.service;

import com.example.testapp.converter.UserConverter;
import com.example.testapp.crud.UserCrud;
import com.example.testapp.dao.UserDao;
import com.example.testapp.dto.AuthUser;
import com.example.testapp.exception.WebException;
import com.example.testapp.security.HashUtills;
import com.example.testapp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserCrud userCrud;

    //Авторизация пользователя по email и паролю
    public AuthUser authorizeUser(String email, String password) {
        try {
            UserDao userDao = authUser(email, password);
            int userId = userDao.getId();
            String tokenLogin = String.format("%d %s", userId, email);
            String token = jwtTokenProvider.createToken(tokenLogin);
            userDao.setPassToken(JwtTokenProvider.BEARER + token);
            userCrud.save(userDao);
            return new AuthUser(UserConverter.daoToUser(userDao), JwtTokenProvider.BEARER + token);
        } catch (Exception e) {
            log.error("Error during authorization", e);
            throw e;
        }
    }

    //Проверка совпадения логина и пароля
    private UserDao authUser(String email, String password) {
        if (!userCrud.existsByEmail(email)) {
            throw new WebException("Login doesn't exist", HttpStatus.UNAUTHORIZED);
        }
        UserDao dao = userCrud.getDistinctByEmail(email);
        if(!Objects.equals(HashUtills.hashPassword(password, dao.getSalt()), dao.getPassword())){
            throw new WebException("Wrong password", HttpStatus.FORBIDDEN);
        }
        return dao;
    }

    //Авторизация пользователя по айди. Используется в случае перехода по ссылке валидации email.
    public AuthUser authorizeNewUser(int id) {
        try {
            UserDao userDao = userCrud.getById(id);
            String email = userDao.getEmail();
            String tokenLogin = String.format("%d %s", id, email);
            String token = jwtTokenProvider.createToken(tokenLogin);
            userDao.setPassToken(JwtTokenProvider.BEARER + token);
            userCrud.save(userDao);
            return new AuthUser(UserConverter.daoToUser(userDao), JwtTokenProvider.BEARER + token);
        } catch (Exception e) {
            log.error("Error during authorization", e);
            throw e;
        }
    }


}

