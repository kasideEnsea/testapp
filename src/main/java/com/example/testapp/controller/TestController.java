package com.example.testapp.controller;

import com.example.testapp.dto.TestDto;
import com.example.testapp.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("/")
    public List<TestDto> getAllTests() {
        return testService.getAll();
    }

    @GetMapping("/{id}")
    public TestDto getTestById(@PathVariable int id){
        return testService.getById(id);
    }

    @PostMapping("/add")
    public TestDto addTest (@RequestBody TestDto test) {
        return testService.addTest(test);
    }

    @PostMapping("/edit")
    public TestDto editTest (@RequestBody TestDto test) {
        return testService.edit(test);
    }

    @PostMapping("/del/{id}")
    public void deleteTest (@PathVariable Integer id) {
        testService.delete(id);
    }
}
