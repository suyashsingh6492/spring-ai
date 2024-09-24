package com.ai.spring.demo.controller;

import com.ai.spring.demo.model.Answer;
import com.ai.spring.demo.model.GetCapitalRequest;
import com.ai.spring.demo.model.Question;
import com.ai.spring.demo.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prompt")
public class QueryController {

    @Autowired
    OpenAIService openAIService;

    @GetMapping
    public String getPrompt() {
        return openAIService.getAnswer("Tell me a bad joke");
    }

    @PostMapping
    public Answer askQuestion(Question question) {
        return openAIService.getAnswer(question);
    }

    @PostMapping("/capital")
    public Answer getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAIService.getCapital(getCapitalRequest);
    }

    @PostMapping("/capital-with-info")
    public Answer getCapitalWithInfo(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAIService.getCapitalWithInfo(getCapitalRequest);
    }

    @PostMapping("/ask")
    public Answer askQuestionMovie(@RequestBody Question question) {
        return openAIService.getAnswerMovie(question);
    }


}
