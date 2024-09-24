package com.ai.spring.demo.service;

import com.ai.spring.demo.model.Answer;
import com.ai.spring.demo.model.GetCapitalRequest;
import com.ai.spring.demo.model.GetCapitalResponse;
import com.ai.spring.demo.model.Question;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OpenAIService {

    String getAnswer(String question);

    Answer getAnswer(Question question);

    Answer getCapital(GetCapitalRequest getCapitalRequest);

    GetCapitalResponse getCapital1(GetCapitalRequest getCapitalRequest);

    Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest);

    Answer getAnswerMovie(Question question);

    Answer getAnswerBoat(Question question);

    Answer getAnswerWeather(Question question);

    Answer getAnswerWeather_2(Question question);

    byte[] getImage(Question question);

    byte[] getImageaOpenAi(Question question);

    String getDescription(MultipartFile file) throws IOException;

    byte[] getSpeech(Question question);
}
