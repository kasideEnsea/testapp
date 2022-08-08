package com.example.testapp.controller;

import com.example.testapp.entity.Test;
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
    public List<Test> getAllTests() {
        return testService.getAll();
    }

    @GetMapping("/{id}")
    public Test getTestById(@PathVariable int id){
        return testService.getById(id);
    }

    @PostMapping("/add")
    public Test addTest (@RequestBody Test test) {
        return testService.addTest(test);
    }

    @PostMapping("/edit")
    public Test editTest (@RequestBody Test test) {
        return testService.edit(test);
    }

    @PostMapping("/del/{id}")
    public void deleteTest (@PathVariable Integer id) {
        testService.delete(id);
    }
}
