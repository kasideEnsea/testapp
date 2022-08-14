package com.example.testapp.converter;

import com.example.testapp.entity.Option;
import com.example.testapp.dto.OptionDto;

public class OptionConverter {
    public static OptionDto entityToOption(Option entity){
        return new OptionDto(entity.getId(), entity.getQuestionId(), entity.getText(), entity.getIsCorrect());
    }

    public static Option optionToEntity(OptionDto option){
        Option entity = new Option();
        entity.setId(option.getId());
        entity.setIsCorrect(option.getIsCorrect());
        entity.setText(option.getText());
        entity.setQuestionId(option.getQuestionId());
        return entity;
    }
}
