package com.example.testapp.controller;

import com.example.testapp.dto.*;
import com.example.testapp.entity.Test;
import com.example.testapp.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
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

    @PostMapping("/getByCode/{code}")
    public CheckedTestDto getCheckedTestByCode(@PathVariable String code){
        return resultService.getCheckedTestByCode(code);
    }

    @GetMapping("/getByEmailAndTestId/{email}/{testId}")
    public CheckedTestDto getCheckedTestByEmailAndTestId(@PathVariable String email, @PathVariable int testId){
        return resultService.getCheckedTestByEmailAndTestId(email, testId);
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
