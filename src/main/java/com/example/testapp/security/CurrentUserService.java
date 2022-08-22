package com.example.testapp.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrentUserService {

    public static int getUserId() {
        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        return TokenUtils.getUserId(subject);
    }
}
