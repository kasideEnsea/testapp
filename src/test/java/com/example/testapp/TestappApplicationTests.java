package com.example.testapp;

import com.example.testapp.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestappApplicationTests {

	@Test
	void contextLoads() {
		MailService mailService = new MailService();
		mailService.sendValidationEmail("tutivanna2@gmail.com", "qwerty", 1);
	}

}
