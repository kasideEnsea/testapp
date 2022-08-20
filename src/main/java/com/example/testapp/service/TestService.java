package com.example.testapp.service;

import com.example.testapp.converter.QuestionConverter;
import com.example.testapp.converter.TestConverter;
import com.example.testapp.dao.QuestionRepository;
import com.example.testapp.dao.TestRepository;
import com.example.testapp.entity.Question;
import com.example.testapp.entity.Test;
import com.example.testapp.dto.QuestionDto;
import com.example.testapp.dto.TestDto;
import com.example.testapp.exception.WebException;
import com.example.testapp.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final QuestionService questionService;


    public LinkedList<TestDto> getAll() {
        LinkedList<Test> daoList = testRepository.getByUserId(CurrentUserService.getUserId());
        LinkedList<TestDto> tests = new LinkedList<>();
        for (Test testDao: daoList
             ) {
            tests.add(TestConverter.entityToTest(testDao, questionService.getAllByTestId(testDao.getId())));
        }
        return tests;
    }

    public TestDto getById(int id) {
        if (!testRepository.existsById(id)) {
            throw new WebException("Test doesnt exists", HttpStatus.NOT_FOUND);
        }
        Test dao = testRepository.getById(id);
        if (dao.getUserId() != CurrentUserService.getUserId()) {
            throw new WebException("Foreign test", HttpStatus.FORBIDDEN);
        }
        return TestConverter.entityToTest(dao, questionService.getAllByTestId(dao.getId()));
    }

    public TestDto addTest(TestDto test){
        if (testRepository.existsById(test.getId())){
            throw new WebException("Test already exists", HttpStatus.BAD_REQUEST);
        }
        if (testRepository.existsByNameAndUserId(test.getName(), CurrentUserService.getUserId())){
            test.setName(test.getName()+"-copy");
        }
        Test testDao = TestConverter.testToEntity(test, CurrentUserService.getUserId());
        testRepository.save(testDao);
        return test;
    }

    public TestDto edit (TestDto test){
        if (!testRepository.existsById(test.getId())){
            throw new WebException("Test doesnt exists", HttpStatus.NOT_FOUND);
        }
        Test testDao = testRepository.getById(test.getId());
        if (testDao.getUserId() != CurrentUserService.getUserId()) {
            throw new WebException("Foreign test", HttpStatus.FORBIDDEN);
        }
        Test newTestDao = TestConverter.testToEntity(test, CurrentUserService.getUserId());
        testRepository.save(newTestDao);

        LinkedList<Question> questionDaos = questionRepository.getByTestId(test.getId());
        for (QuestionDto newQuestion: test.getQuestions()
        ) {
            questionService.addQuestion(newQuestion, test.getId());
        }
        for (Question questionDao: questionDaos
        ) {
            boolean existsInNewTest = false;
            for (QuestionDto newQuestion: test.getQuestions()
            ) {
                if (questionDao.getId().equals(newQuestion.getId())) {
                    existsInNewTest = true;
                    break;
                }
            }
            if (!existsInNewTest){
                questionRepository.delete(questionDao);
            }
        }
        return test;
    }


    public void delete(int testId){
        Test dao = testRepository.getById(testId);
        if (dao.getUserId() != CurrentUserService.getUserId()) {
            throw new WebException("Foreign test", HttpStatus.FORBIDDEN);
        }
        testRepository.deleteById(testId);
    }
}
