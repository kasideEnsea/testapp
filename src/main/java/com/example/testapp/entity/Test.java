package com.example.testapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

@Data
@AllArgsConstructor
public class Test {
    Integer id;
    String name;
    LinkedList<Question> questions;
}

