package com.example.testapp.converter;

import com.example.testapp.entity.Option;
import com.example.testapp.entity.Question;
import com.example.testapp.dto.OptionDto;
import com.example.testapp.dto.QuestionDto;

import java.util.LinkedList;

public class QuestionConverter {
    public static QuestionDto entityToQuestion(Question entity, LinkedList<Option> optionEntities) {
        LinkedList<OptionDto> options = new LinkedList<>();
        for (Option od : optionEntities
        ) {
            options.add(OptionConverter.entityToOption(od));
        }
        return new QuestionDto(entity.getId(), entity.getText(), options);
    }

    public static Question questionToEntity(QuestionDto question, int testId) {
        Question questionEntity = new Question();
        questionEntity.setId(question.getId());
        questionEntity.setTestId(testId);
        questionEntity.setText(question.getText());
        return questionEntity;
    }

}
