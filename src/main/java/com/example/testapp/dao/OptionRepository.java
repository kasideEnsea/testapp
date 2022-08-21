package com.example.testapp.dao;

import com.example.testapp.entity.Option;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedList;

public interface OptionRepository extends CrudRepository<Option, Integer> {
    LinkedList<Option> getAllByQuestionId(int questionId);
    Option getById(int id);
}
