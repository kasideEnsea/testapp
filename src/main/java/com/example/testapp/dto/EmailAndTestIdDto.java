package com.example.testapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailAndTestIdDto {
    String email;
    Integer TestId;
}
