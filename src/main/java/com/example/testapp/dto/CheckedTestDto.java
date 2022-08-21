package com.example.testapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

@Data
@AllArgsConstructor
public class CheckedTestDto {
    Integer id;
    String name;
    LinkedList<CheckedQuestionDto> questions;
    String email;
    Integer countOfRightAnswers;
}
