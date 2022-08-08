package com.example.testapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

@Data
@AllArgsConstructor
public class Question {
    Integer id;
    String text;
    LinkedList<Option> options;
}
