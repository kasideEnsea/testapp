package com.example.testapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    public static final String validationSubject = "Ссылка для подтверждения email";
    public static final String validationText = "Ссылка для подтверждения email для сайта testapp.com.\n";
    public static final String link = "http://localhost:4200/";
    public static final String from = "testapplication@inbox.ru";

    public static final String linkSubject = "Ссылка для прохождения теста";
    public static final String linkText = "Ссылка для прохождения теста";

    @Autowired
    public JavaMailSender emailSender;

    public void sendValidationEmail(String email, String code, int id) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(validationSubject);
        simpleMailMessage.setText(validationText+link+"id:"+id+"/"+"code:"+code);
        simpleMailMessage.setFrom(from);
        emailSender.send(simpleMailMessage);
    }

    public void sendTestLink(String email, String code, int testId, String testName) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(linkSubject);
        simpleMailMessage.setText(linkText+"\""+testName+"\": "+link+"/test/"+"id:"+testId+"/"+"code:"+code);
        simpleMailMessage.setFrom(from);
        emailSender.send(simpleMailMessage);
    }
}
