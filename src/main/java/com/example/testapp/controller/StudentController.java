package com.example.testapp.controller;

import com.example.testapp.dto.*;
import com.example.testapp.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/sendLinks/")
    public void sendLinks(@Valid @RequestBody EmailListDto dto) {
        studentService.sendTestLink(dto);
    }

    @GetMapping("/getTest/{code}")
    public TestDto getTest(@Valid @PathVariable String code) {
        return studentService.getStudentTestByLink(code);
    }

}
