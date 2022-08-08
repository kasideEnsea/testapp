package com.example.testapp.converter;

import com.example.testapp.dao.OptionDao;
import com.example.testapp.entity.Option;

public class OptionConverter {
    public static Option daoToOption(OptionDao dao){
        return new Option(dao.getId(), dao.getQuestionId(), dao.getText(), dao.getIsCorrect());
    }

    public static OptionDao optionToDao(Option option){
        OptionDao dao = new OptionDao();
        dao.setId(option.getId());
        dao.setIsCorrect(option.getIsCorrect());
        dao.setText(option.getText());
        dao.setQuestionId(option.getQuestionId());
        return dao;
    }
}
