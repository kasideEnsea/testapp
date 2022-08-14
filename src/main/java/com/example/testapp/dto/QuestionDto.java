package com.example.testapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

@Data
@AllArgsConstructor
public class QuestionDto {
    Integer id;
    String text;
    LinkedList<OptionDto> options;
}
