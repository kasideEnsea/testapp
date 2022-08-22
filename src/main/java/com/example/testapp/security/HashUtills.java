package com.example.testapp.security;

import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class HashUtills {

    public static String generateSalt(int length){
        byte[]array = new byte[length];
        new Random().nextBytes(array);
        return Base64.getEncoder().encodeToString(array);
    }

    public static String hashPassword(String password, String salt) {
        String passwordWithSalt = password + salt;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(passwordWithSalt.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String generateRandomAlphanumericString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

}
