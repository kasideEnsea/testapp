package com.example.testapp.controller;

import com.example.testapp.dto.*;
import com.example.testapp.service.AuthorizationService;
import com.example.testapp.service.RegistrationService;
import com.example.testapp.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/sendLinks")
    public void sendLinks(@Valid @RequestBody EmailListDto dto) {
        studentService.sendTestLink(dto);
    }

    @PostMapping("/getTest/")
    public TestDto getTest(@Valid @RequestBody ValidDto dto) {
        return studentService.getStudentTestByLink(dto);
    }
}
