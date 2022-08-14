package com.example.testapp.dao;

import com.example.testapp.entity.Attempt;
import org.springframework.data.repository.CrudRepository;

public interface AttemptRepository extends CrudRepository<Attempt, Integer>  {
}
