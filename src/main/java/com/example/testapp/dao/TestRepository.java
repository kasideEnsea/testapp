package com.example.testapp.dao;

import com.example.testapp.entity.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedList;

public interface TestRepository extends CrudRepository<Test, Integer> {
    LinkedList<Test> getByUserId(int userId);
    Test getById (int id);
    boolean existsByNameAndUserId(String name, int id);
}
