package com.example.testapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;

@Data
@AllArgsConstructor
public class OptionDto {
    Integer id;
    Integer questionId;
    String text;
    Boolean isCorrect;
}
