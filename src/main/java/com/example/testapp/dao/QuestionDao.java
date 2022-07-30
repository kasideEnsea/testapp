package com.example.testapp.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "question")
@Data
public class QuestionDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "test_id")
    private Integer testId;
    @Column(name = "text")
    private String text;
}
