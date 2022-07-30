package com.example.testapp.crud;

import com.example.testapp.dao.UserDao;
import org.springframework.data.repository.CrudRepository;

public interface UserCrud extends CrudRepository<UserDao, Integer> {
}
