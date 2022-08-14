package com.example.testapp.dao;

import com.example.testapp.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByEmail(String email);
    User getById(int id);
    ArrayList<User> getByEmail(String email);
    User getDistinctByEmail(String email);
}
