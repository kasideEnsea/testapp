package com.example.testapp.service;

import com.example.testapp.dao.UserRepository;
import com.example.testapp.entity.User;
import com.example.testapp.exception.WebException;
import com.example.testapp.security.HashUtills;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    @Autowired
    public MailService mailService;

    private final UserRepository userRepository;


    public void register(String email, String password, String name) {
        if (userRepository.existsByEmail(email)) {
            if (userRepository.getByEmail(email).get(0).getIsValid()){
                throw new WebException("Email is already used", HttpStatus.BAD_REQUEST);
            }
            throw new WebException("Email is no valid", HttpStatus.FORBIDDEN);
        }

        //Невалидированные данные пользователя сохраняются в базу
        String salt = HashUtills.generateSalt(10);
        password = HashUtills.hashPassword(password, salt);
        User userDao = new User();
        userDao.setEmail(email);
        userDao.setName(name);
        userDao.setPassword(password);
        userDao.setSalt(salt);
        userDao.setIsValid(false);
        userRepository.save(userDao);

        //Генерируется рандомная ссылка для подтверждения email, сохраняется в базу и отправляется на почту пользователя
        User savedUser = userRepository.getDistinctByEmail(email);
        String code = HashUtills.generateRandomAlphanumericString(10);
        mailService.sendValidationEmail(email, code, savedUser.getId());
        savedUser.setValidationCode(code);
        userRepository.save(savedUser);
    }

    //Сравнение ссылки для подтверждения email из базы с ссылкой, по которой перешел пользователь
    public void validate(int id, String code){
        if (!userRepository.existsById(id)) {
            throw new WebException("User is not found", HttpStatus.NOT_FOUND);
        }
        User userDao = userRepository.getById(id);
        if (!userDao.getValidationCode().equals(code)){
            throw new WebException("Wrong link", HttpStatus.FORBIDDEN);
        }
        userDao.setValidationCode(null);
        userDao.setIsValid(true);
        ArrayList<User> sameEmails = userRepository.getByEmail(userDao.getEmail());
        for (User se: sameEmails
             ) {
            if (se.getId()!=id){
                userRepository.delete(se);
            }
        }
    }
}
