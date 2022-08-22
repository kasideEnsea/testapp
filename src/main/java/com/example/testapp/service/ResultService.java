package com.example.testapp.service;

import com.example.testapp.dao.AttemptRepository;
import com.example.testapp.dao.LinkRepository;
import com.example.testapp.dao.OptionRepository;
import com.example.testapp.dao.TestRepository;
import com.example.testapp.dto.*;
import com.example.testapp.entity.*;
import com.example.testapp.exception.WebException;
import com.example.testapp.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final TestRepository testRepository;
    private final LinkRepository linkRepository;
    private final AttemptRepository attemptRepository;
    private final OptionRepository optionRepository;
    private final TestService testService;

    public void saveAttempt (TestDto test, String code) {
        if (!testRepository.existsById(test.getId())){
            throw new WebException("Test doesnt exists", HttpStatus.NOT_FOUND);
        }
        if (!linkRepository.existsByRandomLink(code)){
            throw new WebException("Link doesnt exists", HttpStatus.NOT_FOUND);
        }
        Link link = linkRepository.getByRandomLink(code);
        int countOfRightAnswers = 0;
        for (QuestionDto question: test.getQuestions()
             ) {
            boolean right = true;
            for (OptionDto optionDto: question.getOptions()
                 ) {
                if (optionDto.getIsCorrect()){
                    Attempt attempt = new Attempt();
                    attempt.setLinkId(link.getId());
                    attempt.setOptionId(optionDto.getId());
                    attemptRepository.save(attempt);
                }
                Option correctOption = optionRepository.getById(optionDto.getId());
                if (!optionDto.getIsCorrect().equals(correctOption.getIsCorrect())){
                    right = false;
                }
            }
            if (right){
                countOfRightAnswers++;
            }
        }
        link.setRightAnswersCount(countOfRightAnswers);
        linkRepository.save(link);
    }

    public CheckedTestDto getCheckedTestByCode(String code){
        if (!linkRepository.existsByRandomLink(code)){
            throw new WebException("Link doesnt exists", HttpStatus.NOT_FOUND);
        }
        Link link = linkRepository.getByRandomLink(code);
        return check(link);
    }

    public CheckedTestDto getCheckedTestByLinkId(int linkId) {
        if (!linkRepository.existsById(linkId)){
            throw new WebException("Link doesnt exists", HttpStatus.NOT_FOUND);
        }
        Link link = linkRepository.getById(linkId);
        return check(link);
    }

    private CheckedTestDto check (Link link){
        TestDto testDto = testService.getByIdUnauthorized(link.getTestId());
        LinkedList<CheckedQuestionDto> checkedQuestions = new LinkedList<>();
        for (QuestionDto question: testDto.getQuestions()
        ) {
            LinkedList<CheckedOptionDto> checkedOptions = new LinkedList<>();
            for (OptionDto optionDto: question.getOptions()
            ) {
                boolean isChosen = attemptRepository.existsByLinkIdAndOptionId(link.getId(), optionDto.getId());
                CheckedOptionDto checkedOptionDto = new CheckedOptionDto(optionDto.getId(), optionDto.getQuestionId(),
                        optionDto.getText(), isChosen, optionDto.getIsCorrect());
                checkedOptions.add(checkedOptionDto);
            }
            checkedQuestions.add(new CheckedQuestionDto(question.getId(), question.getText(), checkedOptions));
        }
        return new CheckedTestDto(testDto.getId(),
                testDto.getName(), checkedQuestions, link.getEmail(), link.getRightAnswersCount(), link.getId());
    }

    public LinkedList<CheckedTestDto> getAllByTestId(Integer id) {
        LinkedList<CheckedTestDto> checkedTestDtos = new LinkedList<>();
        LinkedList<Link> links = linkRepository.getAllByTestId(id);
        Test test = testRepository.getById(id);
        if (test.getUserId() != CurrentUserService.getUserId()) {
            throw new WebException("Foreign test", HttpStatus.FORBIDDEN);
        }
        for (Link link: links
             ) {
            if (link.getRightAnswersCount()>0){
                checkedTestDtos.add(check(link));
            }
        }
        return checkedTestDtos;
    }

    public LinkedList<CheckedTestDto> getAllByCode(String code) {
        LinkedList<CheckedTestDto> checkedTestDtos = new LinkedList<>();
        if (!linkRepository.existsByRandomLink(code)){
            throw new WebException("Wrong link", HttpStatus.FORBIDDEN);
        }
        Link currentLink = linkRepository.getByRandomLink(code);
        LinkedList<Link> links = linkRepository.getAllByEmail(currentLink.getEmail());
        for (Link link: links
        ) {
            if (link.getRightAnswersCount()>0){
                checkedTestDtos.add(check(link));
            }
        }
        return checkedTestDtos;
    }
}
