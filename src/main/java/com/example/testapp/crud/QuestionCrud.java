package com.example.testapp.crud;

import com.example.testapp.dao.QuestionDao;
import org.springframework.data.repository.CrudRepository;

public interface QuestionCrud extends CrudRepository<QuestionDao, Integer> {
}
