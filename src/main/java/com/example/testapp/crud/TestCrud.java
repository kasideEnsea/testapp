package com.example.testapp.crud;

import com.example.testapp.dao.TestDao;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedList;

public interface TestCrud extends CrudRepository<TestDao, Integer> {
    LinkedList<TestDao> getByUserId(int userId);
    TestDao getById (int id);
    boolean existsByName(String name);
}
