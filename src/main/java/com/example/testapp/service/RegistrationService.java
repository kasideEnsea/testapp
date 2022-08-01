package com.example.testapp.service;

import com.example.testapp.crud.UserCrud;
import com.example.testapp.dao.UserDao;
import com.example.testapp.exception.WebException;
import com.example.testapp.security.HashUtills;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    @Autowired
    public MailService mailService;

    private final UserCrud userCrud;


    public void register(String email, String password, String name) {
        if (userCrud.existsByEmail(email)) {
            if (userCrud.getDistinctByEmail(email).getIsValid()){
                throw new WebException("Email is already used", HttpStatus.BAD_REQUEST);
            }
        }

        //Невалидированные данные пользователя сохраняются в базу
        String salt = HashUtills.generateSalt(10);
        password = HashUtills.hashPassword(password, salt);
        UserDao userDao = new UserDao();
        userDao.setEmail(email);
        userDao.setName(name);
        userDao.setPassword(password);
        userDao.setSalt(salt);
        userDao.setIsValid(false);
        userCrud.save(userDao);

        //Генерируется рандомная ссылка для подтверждения email, сохраняется в базу и отправляется на почту пользователя
        UserDao savedUser = userCrud.getDistinctByEmail(email);
        String code = HashUtills.generateRandomAlphanumericString(10);
        mailService.sendValidationEmail(email, code, savedUser.getId());
        savedUser.setValidationCode(code);
        userCrud.save(savedUser);
    }

    //Сравнение ссылки для подтверждения email из базы с ссылкой, по которой перешел пользователь
    public void validate(int id, String code){
        if (!userCrud.existsById(id)) {
            throw new WebException("User is not found", HttpStatus.NOT_FOUND);
        }
        UserDao userDao = userCrud.getById(id);
        if (!userDao.getValidationCode().equals(code)){
            throw new WebException("Wrong link", HttpStatus.FORBIDDEN);
        }
        userDao.setValidationCode(null);
        userDao.setIsValid(true);
        ArrayList<UserDao> sameEmails = userCrud.getByEmail(userDao.getEmail());
        for (UserDao se: sameEmails
             ) {
            if (se.getId()!=id){
                userCrud.delete(se);
            }
        }
    }
}
