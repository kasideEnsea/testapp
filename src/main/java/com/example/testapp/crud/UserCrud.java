package com.example.testapp.crud;

import com.example.testapp.dao.UserDao;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface UserCrud extends CrudRepository<UserDao, Integer> {
    boolean existsByEmail(String email);
    UserDao getDistinctByEmail(String email);
    UserDao getById(int id);
    ArrayList<UserDao> getByEmail(String email);
}
