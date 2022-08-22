package com.example.testapp.controller;

import com.example.testapp.dto.*;
import com.example.testapp.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
public class ResultController {
    private final ResultService resultService;

    @PostMapping("/saveAttempt/{code}")
    public void saveAttempt (@Valid @RequestBody TestDto test, @PathVariable String code) {
        resultService.saveAttempt(test, code);
    }

    @GetMapping("/getByCode/{code}")
    public CheckedTestDto getCheckedTestByCode(@PathVariable String code){
        return resultService.getCheckedTestByCode(code);
    }

    @GetMapping("/getCheckedTestByLinkId/{linkId}")
    public CheckedTestDto getCheckedTestByLinkId(@PathVariable int linkId){
        return resultService.getCheckedTestByLinkId(linkId);
    }

    @GetMapping("/getAllByTestId/{id}")
    public List<CheckedTestDto> getAllByTestId(@PathVariable Integer id){
        return resultService.getAllByTestId(id);
    }

    @GetMapping("/getAllByCode/{code}")
    public List<CheckedTestDto> getAllByCode(@PathVariable String code){
        return resultService.getAllByCode(code);
    }

}
