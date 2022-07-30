package com.example.testapp.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "option")
@Data
public class OptionDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "question_id")
    private Integer questionId;
    @Column(name = "text")
    private String text;
    @Column(name = "isCorrect")
    private Boolean isCorrect;
}
