package com.example.testapp.crud;

import com.example.testapp.dao.AttemptDao;
import org.springframework.data.repository.CrudRepository;

public interface AttemptCrud extends CrudRepository<AttemptDao, Integer>  {
}
