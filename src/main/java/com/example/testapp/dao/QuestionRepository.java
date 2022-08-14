package com.example.testapp.dao;

import com.example.testapp.entity.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedList;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
    LinkedList<Question> getByTestId (int testId);
    Question getById (int id);
}
