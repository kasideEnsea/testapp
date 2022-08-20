package com.example.testapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "option")
@Data
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "question_id")
    private Integer questionId;
    @Column(name = "text")
    private String text;
    @Column(name = "iscorrect")
    private Boolean isCorrect;
}
