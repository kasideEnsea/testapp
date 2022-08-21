package com.example.testapp.service;

import com.example.testapp.converter.TestConverter;
import com.example.testapp.dao.LinkRepository;
import com.example.testapp.dao.TestRepository;
import com.example.testapp.dao.UserRepository;
import com.example.testapp.dto.*;
import com.example.testapp.entity.Link;
import com.example.testapp.entity.Test;
import com.example.testapp.entity.User;
import com.example.testapp.exception.WebException;
import com.example.testapp.security.HashUtills;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class StudentService {
    @Autowired
    public MailService mailService;

    private final LinkRepository linkRepository;
    private final TestRepository testRepository;
    private final TestService testService;

    public void sendTestLink(EmailListDto emailListDto){
        if (!testRepository.existsById(emailListDto.getTestId())){
            throw new WebException("Test is not found", HttpStatus.NOT_FOUND);
        }
        for (String email: emailListDto.getEmails()
             ) {
            if (email.length()==0){
                return;
            }
            Test test = testRepository.getById(emailListDto.getTestId());
            if (linkRepository.existsByEmailAndTestId(email, emailListDto.getTestId())){
               linkRepository.deleteByEmailAndTestId(email, emailListDto.getTestId());
            }
            String code = HashUtills.generateRandomAlphanumericString(10);
            mailService.sendTestLink(email, code, emailListDto.getTestId(), test.getName());
            Link link = new Link(email, emailListDto.getTestId(), code);
            linkRepository.save(link);
        }
    }

    public TestDto getStudentTestByLink(ValidDto dto) {
        if (!linkRepository.existsByTestIdAndRandomLink(dto.getId(), dto.getCode())){
            throw new WebException("Wrong link", HttpStatus.FORBIDDEN);
        }
        return getEmptyTest(testService.getById(dto.getId()));
    }

    private TestDto getEmptyTest(TestDto testDto){
        for (QuestionDto question: testDto.getQuestions()
             ) {
            for (OptionDto option: question.getOptions()
                 ) {
                option.setIsCorrect(false);
            }
        }
        return testDto;
    }
}
