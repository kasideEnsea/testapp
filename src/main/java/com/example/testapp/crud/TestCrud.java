package com.example.testapp.crud;

import com.example.testapp.dao.TestDao;
import org.springframework.data.repository.CrudRepository;

public interface TestCrud extends CrudRepository<TestDao, Integer> {
}
