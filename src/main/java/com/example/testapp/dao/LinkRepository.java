package com.example.testapp.dao;

import com.example.testapp.entity.Link;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, Integer> {
}
