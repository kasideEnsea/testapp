package com.example.testapp.service;

import com.example.testapp.converter.UserConverter;
import com.example.testapp.dao.UserRepository;
import com.example.testapp.entity.User;
import com.example.testapp.dto.AuthUserDto;
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
    private final UserRepository userRepository;

    //Авторизация пользователя по email и паролю
    public AuthUserDto authorizeUser(String email, String password) {
        try {
            User userDao = authUser(email, password);
            int userId = userDao.getId();
            String tokenLogin = String.format("%d %s", userId, email);
            String token = jwtTokenProvider.createToken(tokenLogin);
            userDao.setPassToken(JwtTokenProvider.BEARER + token);
            userRepository.save(userDao);
            return new AuthUserDto(UserConverter.entityToUser(userDao), JwtTokenProvider.BEARER + token);
        } catch (Exception e) {
            log.error("Error during authorization", e);
            throw e;
        }
    }

    //Проверка совпадения логина и пароля
    private User authUser(String email, String password) {
        if (!userRepository.existsByEmail(email)) {
            throw new WebException("Login doesn't exist", HttpStatus.UNAUTHORIZED);
        }
        User dao = userRepository.getDistinctByEmail(email);
        if(!Objects.equals(HashUtills.hashPassword(password, dao.getSalt()), dao.getPassword())){
            throw new WebException("Wrong password", HttpStatus.FORBIDDEN);
        }
        return dao;
    }

    //Авторизация пользователя по айди. Используется в случае перехода по ссылке валидации email.
    public AuthUserDto authorizeNewUser(int id) {
        try {
            User userDao = userRepository.getById(id);
            String email = userDao.getEmail();
            String tokenLogin = String.format("%d %s", id, email);
            String token = jwtTokenProvider.createToken(tokenLogin);
            userDao.setPassToken(JwtTokenProvider.BEARER + token);
            userRepository.save(userDao);
            return new AuthUserDto(UserConverter.entityToUser(userDao), JwtTokenProvider.BEARER + token);
        } catch (Exception e) {
            log.error("Error during authorization", e);
            throw e;
        }
    }


}

