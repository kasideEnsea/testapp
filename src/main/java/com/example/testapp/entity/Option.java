package com.example.testapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;

@Data
@AllArgsConstructor
public class Option {
    Integer id;
    Integer questionId;
    String text;
    Boolean isCorrect;
}
