package com.example.testapp.service;
import com.example.testapp.converter.OptionConverter;
import com.example.testapp.converter.QuestionConverter;
import com.example.testapp.crud.OptionCrud;
import com.example.testapp.crud.QuestionCrud;
import com.example.testapp.dao.OptionDao;
import com.example.testapp.dao.QuestionDao;
import com.example.testapp.entity.Option;
import com.example.testapp.entity.Question;
import com.example.testapp.exception.WebException;
import com.example.testapp.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionCrud questionCrud;
    private final OptionCrud optionCrud;

    public LinkedList<Question> getAllByTestId(int testId) {
        LinkedList<QuestionDao> daoList = questionCrud.getByTestId(testId);
        LinkedList<Question> questions = new LinkedList<>();
        for (QuestionDao questionDao: daoList
        ) {
            LinkedList<OptionDao> optionDaos = optionCrud.getByQuestionId(questionDao.getId());
            questions.add(QuestionConverter.daoToQuestion(questionDao, optionDaos));
        }
        return questions;
    }

    public Question getById(int id){
        if (!questionCrud.existsById(id)) {
            throw new WebException("Question doesn't exist", HttpStatus.NOT_FOUND);
        }
        QuestionDao dao = questionCrud.getById(id);
        LinkedList<OptionDao> optionDaos = optionCrud.getByQuestionId(dao.getId());
        return QuestionConverter.daoToQuestion(dao, optionDaos);
    }

    public Question addQuestion(Question question, int testId){
        if (questionCrud.existsById(question.getId())) {
            throw new WebException("Question exists", HttpStatus.BAD_REQUEST);
        }
        LinkedList<OptionDao> optionDaos = new LinkedList<>();
        for (Option option: question.getOptions()
             ) {
            option.setQuestionId(question.getId());
            OptionDao optionDao = OptionConverter.optionToDao(option);
            optionCrud.save(optionDao);
        }
        QuestionDao questionDao = QuestionConverter.questionToDao(question, testId);
        questionCrud.save(questionDao);
        return question;
    }

    public void deleteQuestion(int questionId){
        questionCrud.deleteById(questionId);
    }

    public Question editQuestion (Question question){
        if (!questionCrud.existsById(question.getId())){
            throw new WebException("Question doesn't exist", HttpStatus.NOT_FOUND);
        }
        QuestionDao oldQuestionDao = questionCrud.getById(question.getId());
        if (!question.getText().equals(oldQuestionDao.getText())){
            oldQuestionDao.setText(question.getText());
        }
        questionCrud.save(oldQuestionDao);
        LinkedList<OptionDao> optionDaos = optionCrud.getByQuestionId(question.getId());
        for (Option newOption: question.getOptions()
             ) {
            newOption.setQuestionId(question.getId());
            OptionDao optionDao = OptionConverter.optionToDao(newOption);
            optionCrud.save(optionDao);
            }
        for (OptionDao optionDao: optionDaos
             ) {
            boolean existsInNewQuestion = false;
            for (Option newOption: question.getOptions()
                 ) {
                if (optionDao.getId().equals(newOption.getId())) {
                    existsInNewQuestion = true;
                    break;
                }
            }
            if (!existsInNewQuestion){
                optionCrud.delete(optionDao);
            }
        }
        return question;
    }
}
