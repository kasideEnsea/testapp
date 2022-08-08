package com.example.testapp.converter;

import com.example.testapp.dao.OptionDao;
import com.example.testapp.dao.QuestionDao;
import com.example.testapp.entity.Option;
import com.example.testapp.entity.Question;

import java.util.LinkedList;

public class QuestionConverter {
    public static Question daoToQuestion(QuestionDao dao, LinkedList<OptionDao> optionDaos) {
        LinkedList<Option> options = new LinkedList<>();
        for (OptionDao od : optionDaos
        ) {
            options.add(OptionConverter.daoToOption(od));
        }
        return new Question(dao.getId(), dao.getText(), options);
    }

    public static QuestionDao questionToDao(Question question, int testId) {
        QuestionDao questionDao = new QuestionDao();
        questionDao.setId(question.getId());
        questionDao.setTestId(testId);
        questionDao.setText(question.getText());
        return questionDao;
    }

}
