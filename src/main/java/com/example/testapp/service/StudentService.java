package com.example.testapp.service;

import com.example.testapp.dao.LinkRepository;
import com.example.testapp.dao.TestRepository;
import com.example.testapp.dto.*;
import com.example.testapp.entity.Link;
import com.example.testapp.entity.Test;
import com.example.testapp.exception.WebException;
import com.example.testapp.security.HashUtills;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
                Link link = linkRepository.getByEmailAndTestId(email, emailListDto.getTestId());
                linkRepository.delete(link);
            }
            String code = HashUtills.generateRandomAlphanumericString(10);
            while (linkRepository.existsByRandomLink(code)){
                code = HashUtills.generateRandomAlphanumericString(10);
            }
            mailService.sendTestLink(email, code, test.getName());
            Link link = new Link(email, emailListDto.getTestId(), code);
            linkRepository.save(link);
        }
    }

    public TestDto getStudentTestByLink(String code) {
        if (linkRepository.existsByRandomLink(code)){
            Link link = linkRepository.getByRandomLink(code);
            if (link.getRightAnswersCount()<0) {
                TestDto test = testService.getByIdUnauthorized(link.getTestId());
                return getEmptyTest(test);
            }
        }
        throw new WebException("Wrong link", HttpStatus.FORBIDDEN);
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
