package com.example.testapp.crud;

import com.example.testapp.dao.OptionDao;
import com.example.testapp.entity.Option;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedList;

public interface OptionCrud extends CrudRepository<OptionDao, Integer> {
    LinkedList<OptionDao> getByQuestionId(int questionId);
}
