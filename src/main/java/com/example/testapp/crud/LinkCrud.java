package com.example.testapp.crud;

import com.example.testapp.dao.LinkDao;
import org.springframework.data.repository.CrudRepository;

public interface LinkCrud extends CrudRepository<LinkDao, Integer> {
}
