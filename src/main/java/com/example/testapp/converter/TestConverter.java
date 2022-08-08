package com.example.testapp.converter;

import com.example.testapp.dao.QuestionDao;
import com.example.testapp.dao.TestDao;
import com.example.testapp.entity.Question;
import com.example.testapp.entity.Test;

import java.util.LinkedList;

public class TestConverter {
    public static Test daoToTest (TestDao dao, LinkedList<Question> questions){
        return new Test(dao.getId(), dao.getName(), questions);
    }

    public static TestDao testToDao (Test test, int userId){
        TestDao testDao = new TestDao();
        testDao.setId(test.getId());
        testDao.setName(test.getName());
        testDao.setUserId(userId);
        return testDao;
    }
}
