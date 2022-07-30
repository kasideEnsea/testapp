package com.example.testapp.crud;

import com.example.testapp.dao.OptionDao;
import org.springframework.data.repository.CrudRepository;

public interface OptionCrud extends CrudRepository<OptionDao, Integer> {
}
