package com.example.testapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

@Data
@AllArgsConstructor
public class TestDto {
    Integer id;
    String name;
    LinkedList<QuestionDto> questions;
}

