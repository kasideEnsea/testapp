package com.example.testapp.service;
import com.example.testapp.converter.OptionConverter;
import com.example.testapp.converter.QuestionConverter;
import com.example.testapp.dao.OptionRepository;
import com.example.testapp.dao.QuestionRepository;
import com.example.testapp.entity.Option;
import com.example.testapp.entity.Question;
import com.example.testapp.dto.OptionDto;
import com.example.testapp.dto.QuestionDto;
import com.example.testapp.exception.WebException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    public LinkedList<QuestionDto> getAllByTestId(int testId) {
        LinkedList<Question> daoList = questionRepository.getByTestId(testId);
        LinkedList<QuestionDto> questions = new LinkedList<>();
        for (Question questionDao: daoList
        ) {
            LinkedList<Option> optionDaos = optionRepository.getAllByQuestionId(questionDao.getId());
            questions.add(QuestionConverter.entityToQuestion(questionDao, optionDaos));
        }
        return questions;
    }

    public QuestionDto getById(int id){
        if (!questionRepository.existsById(id)) {
            throw new WebException("Question doesn't exist", HttpStatus.NOT_FOUND);
        }
        Question dao = questionRepository.getById(id);
        LinkedList<Option> optionDaos = optionRepository.getAllByQuestionId(dao.getId());
        return QuestionConverter.entityToQuestion(dao, optionDaos);
    }

    public QuestionDto addQuestion(QuestionDto question, int testId){
        if (questionRepository.existsById(question.getId())) {
            return editQuestion(question);
        }
        Question questionDao = QuestionConverter.questionToEntity(question, testId);
        Question savedDao = questionRepository.save(questionDao);
        for (OptionDto option: question.getOptions()
             ) {
            if (option.getText().length()>0){
                option.setQuestionId(question.getId());
                option.setQuestionId(savedDao.getId());
                Option optionDao = OptionConverter.optionToEntity(option);
                optionRepository.save(optionDao);
            }
        }

        return question;
    }

    public void deleteQuestion(int questionId){
        if (questionRepository.existsById(questionId)) {
            LinkedList<Option> options = optionRepository.getAllByQuestionId(questionId);
            for (Option option: options
            ) {
                optionRepository.deleteById(option.getId());
            }
            questionRepository.deleteById(questionId);
        }

    }

    public QuestionDto editQuestion (QuestionDto question){
        if (!questionRepository.existsById(question.getId())){
            throw new WebException("Question doesn't exist", HttpStatus.NOT_FOUND);
        }
        Question oldQuestionDao = questionRepository.getById(question.getId());
        if (!question.getText().equals(oldQuestionDao.getText())){
            oldQuestionDao.setText(question.getText());
        }
        questionRepository.save(oldQuestionDao);
        //LinkedList<Option> optionDaos = optionRepository.getAllByQuestionId(question.getId());
        for (OptionDto newOption: question.getOptions()) {
            if (newOption.getText().length()>0) {
                newOption.setQuestionId(question.getId());
                Option optionDao = OptionConverter.optionToEntity(newOption);
                optionRepository.save(optionDao);
            }
            else{
                if (optionRepository.existsById(newOption.getId())){
                    optionRepository.deleteById(newOption.getId());
                }
            }
        }
        /*for (Option optionDao: optionDaos
             ) {
            boolean existsInNewQuestion = false;
            for (OptionDto newOption: question.getOptions()
                 ) {
                if (newOption.getText().length()>0 && optionDao.getId().equals(newOption.getId())) {
                    existsInNewQuestion = true;
                    break;
                }
            }
            if (!existsInNewQuestion){
                optionRepository.delete(optionDao);
            }
        }*/
        return question;
    }

    public QuestionDto addOptionInQuestion(OptionDto optionDto, int id){
        Option option = OptionConverter.optionToEntity(optionDto);
        optionRepository.save(option);
        return getById(id);
    }
}
