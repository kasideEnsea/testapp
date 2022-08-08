package com.example.testapp.controller;


import com.example.testapp.dto.AuthUser;
import com.example.testapp.dto.LoginDataDto;
import com.example.testapp.dto.ValidDto;
import com.example.testapp.service.AuthorizationService;
import com.example.testapp.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final AuthorizationService authorizationService;

    @PostMapping("/")
    public void register(@Valid @RequestBody LoginDataDto dto) {
        registrationService.register(dto.getEmail(), dto.getPassword(), dto.getName());
    }

    //Подтверждение адреса почты пользователя
    @PostMapping("/valid/")
    public AuthUser validate(@Valid @RequestBody ValidDto dto) {
        System.out.println(dto);
        int id = dto.getId();
        String code = dto.getCode();
        registrationService.validate(id, code);
        return authorizationService.authorizeNewUser(id);
    }
}

