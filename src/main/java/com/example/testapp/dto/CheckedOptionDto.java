package com.example.testapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckedOptionDto {
    Integer id;
    Integer questionId;
    String text;
    Boolean isChosen;
    Boolean isCorrect;
}
