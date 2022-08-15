package com.example.testapp.controller;


import com.example.testapp.dto.OptionDto;
import com.example.testapp.dto.QuestionDto;
import com.example.testapp.dto.TestDto;
import com.example.testapp.entity.Option;
import com.example.testapp.entity.Question;
import com.example.testapp.service.QuestionService;
import com.example.testapp.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/addopt/{id}")
    public QuestionDto addOption (@PathVariable Integer id, @RequestBody OptionDto option) {
        return questionService.addOptionInQuestion(option, id);
    }

    @PostMapping("/add/{id}")
    public QuestionDto addQuestion (@PathVariable Integer id, @RequestBody QuestionDto questionDto) {
        return questionService.addQuestion(questionDto, id);
    }

    @PostMapping("/del/{id}")
    public void deleteQuestion (@PathVariable Integer id) {
        questionService.deleteQuestion(id);
    }
}
