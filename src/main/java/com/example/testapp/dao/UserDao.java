package com.example.testapp.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "testapp_user")
@Data
public class UserDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "pass_token")
    private String passToken;
    @Column(name = "salt")
    private String salt;
    @Column(name = "name")
    private String name;
    @Column(name = "validation_code")
    private String validationCode;
    @Column(name = "is_valid")
    private Boolean isValid;
}
