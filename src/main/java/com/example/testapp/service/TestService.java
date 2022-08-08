package com.example.testapp.service;

import com.example.testapp.converter.QuestionConverter;
import com.example.testapp.converter.TestConverter;
import com.example.testapp.crud.QuestionCrud;
import com.example.testapp.crud.TestCrud;
import com.example.testapp.dao.QuestionDao;
import com.example.testapp.dao.TestDao;
import com.example.testapp.entity.Question;
import com.example.testapp.entity.Test;
import com.example.testapp.exception.WebException;
import com.example.testapp.security.CurrentUserService;
import liquibase.pro.packaged.L;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.Convert;
import javax.persistence.Converter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestCrud testCrud;
    private final QuestionCrud questionCrud;
    private final QuestionService questionService;


    public LinkedList<Test> getAll() {
        LinkedList<TestDao> daoList = testCrud.getByUserId(CurrentUserService.getUserId());
        LinkedList<Test> tests = new LinkedList<>();
        for (TestDao testDao: daoList
             ) {
            tests.add(TestConverter.daoToTest(testDao, questionService.getAllByTestId(testDao.getId())));
        }
        return tests;
    }

    public Test getById(int id) {
        if (!testCrud.existsById(id)) {
            throw new WebException("Test doesnt exists", HttpStatus.NOT_FOUND);
        }
        TestDao dao = testCrud.getById(id);
        if (dao.getUserId() != CurrentUserService.getUserId()) {
            throw new WebException("Foreign test", HttpStatus.FORBIDDEN);
        }
        return TestConverter.daoToTest(dao, questionService.getAllByTestId(dao.getId()));
    }

    public Test addTest(Test test){
        if (testCrud.existsById(test.getId()) || testCrud.existsByName(test.getName())){
            throw new WebException("Test already exists", HttpStatus.BAD_REQUEST);
        }
        TestDao testDao = TestConverter.testToDao(test, CurrentUserService.getUserId());
        testCrud.save(testDao);
        return test;
    }

    public Test edit (Test test){
        if (!testCrud.existsById(test.getId())){
            throw new WebException("Test doesnt exists", HttpStatus.NOT_FOUND);
        }
        TestDao testDao = testCrud.getById(test.getId());
        if (testDao.getUserId() != CurrentUserService.getUserId()) {
            throw new WebException("Foreign test", HttpStatus.FORBIDDEN);
        }
        TestDao  newTestDao = TestConverter.testToDao(test, CurrentUserService.getUserId());
        testCrud.save(newTestDao);

        LinkedList<QuestionDao> questionDaos = questionCrud.getByTestId(test.getId());
        for (Question newQuestion: test.getQuestions()
        ) {
            QuestionDao questionDao = QuestionConverter.questionToDao(newQuestion, test.getId());
            questionCrud.save(questionDao);
        }
        for (QuestionDao questionDao: questionDaos
        ) {
            boolean existsInNewTest = false;
            for (Question newQuestion: test.getQuestions()
            ) {
                if (questionDao.getId().equals(newQuestion.getId())) {
                    existsInNewTest = true;
                    break;
                }
            }
            if (!existsInNewTest){
                questionCrud.delete(questionDao);
            }
        }
        return test;
    }



    public void delete(int testId){
        TestDao dao = testCrud.getById(testId);
        if (dao.getUserId() != CurrentUserService.getUserId()) {
            throw new WebException("Foreign test", HttpStatus.FORBIDDEN);
        }
        testCrud.deleteById(testId);
    }
}
