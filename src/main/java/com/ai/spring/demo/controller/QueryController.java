package com.ai.spring.demo.controller;

import com.ai.spring.demo.model.Answer;
import com.ai.spring.demo.model.GetCapitalRequest;
import com.ai.spring.demo.model.Question;
import com.ai.spring.demo.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping("/weather")
    public Answer askQuestionW(@RequestBody Question question) {
        return openAIService.getAnswerWeather(question);
    }

    @PostMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@RequestBody  Question question) {
        return openAIService.getImage(question);
    }

    @PostMapping(value = "/vision", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> upload(
            @Validated @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name
    ) throws IOException {

        return ResponseEntity.ok(openAIService.getDescription(file));
    }



    @PostMapping(value ="/talk", produces = "audio/mpeg")
    public byte[] talkTalk(@RequestBody Question question) {
        return openAIService.getSpeech(question);
    }

}
