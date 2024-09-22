package com.ai.spring.demo.service;

import com.ai.spring.demo.service.OpenAIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenAIServiceImplTest {

    @Autowired
    OpenAIService openAIService;

    @Test
    void getAnswer() {
        String answer = openAIService.getAnswer("Tell me a bad joke.");
        System.out.println("Answer is :");
        System.out.println(answer);
    }
}