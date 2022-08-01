package com.example.testapp.controller;

import com.example.testapp.dto.AuthUser;
import com.example.testapp.dto.LoginDataDto;
import com.example.testapp.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @PostMapping("/")
    public AuthUser authUser(@Valid @RequestBody LoginDataDto loginOptionsDto) {
        return authorizationService.authorizeUser(loginOptionsDto.getEmail(), loginOptionsDto.getPassword());
    }
}
