package com.example.testapp.dao;

import com.example.testapp.entity.Link;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedList;

public interface LinkRepository extends CrudRepository<Link, Integer> {
    boolean existsByEmailAndTestId(String email, int testId);
    Link getByRandomLink(String randomLink);
    Link getByEmailAndTestId(String email, int testId);
    void deleteByEmailAndTestId(String email, int testId);
    boolean existsByRandomLink(String randomLink);
    LinkedList<Link> getAllByTestId(int testId);
    LinkedList<Link> getAllByEmail(String email);
}
