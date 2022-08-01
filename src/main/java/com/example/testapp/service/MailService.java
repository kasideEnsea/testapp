package com.example.testapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    public static final String validationSubject = "Ссылка для подтверждения email";
    public static final String validationText = "Ссылка для подтверждения email для сайта testapp.com.\n";
    public static final String registrationCodeLink = "http://localhost:4200/registration/";

    @Autowired
    public JavaMailSender emailSender;

    public void sendValidationEmail(String email, String code, int id) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(validationSubject);
        simpleMailMessage.setText(validationText+registrationCodeLink+id+'/'+code);
        emailSender.send(simpleMailMessage);
    }
}
