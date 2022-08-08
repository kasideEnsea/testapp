package com.example.testapp.crud;

import com.example.testapp.dao.QuestionDao;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedList;

public interface QuestionCrud extends CrudRepository<QuestionDao, Integer> {
    LinkedList<QuestionDao> getByTestId (int testId);
}
