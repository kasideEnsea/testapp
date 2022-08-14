package com.example.testapp.converter;

import com.example.testapp.entity.Test;
import com.example.testapp.dto.QuestionDto;
import com.example.testapp.dto.TestDto;

import java.util.LinkedList;

public class TestConverter {
    public static TestDto entityToTest(Test entity, LinkedList<QuestionDto> questions){
        return new TestDto(entity.getId(), entity.getName(), questions);
    }

    public static Test testToEntity(TestDto test, int userId){
        Test testEntity = new Test();
        testEntity.setId(test.getId());
        testEntity.setName(test.getName());
        testEntity.setUserId(userId);
        return testEntity;
    }
}
