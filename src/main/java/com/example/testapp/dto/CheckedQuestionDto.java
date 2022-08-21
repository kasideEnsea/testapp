package com.example.testapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

@Data
@AllArgsConstructor
public class CheckedQuestionDto {
    Integer id;
    String text;
    LinkedList<CheckedOptionDto> options;
}
