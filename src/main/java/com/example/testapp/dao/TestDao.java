package com.example.testapp.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "test")
@Data
public class TestDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "name")
    private String name;
}
