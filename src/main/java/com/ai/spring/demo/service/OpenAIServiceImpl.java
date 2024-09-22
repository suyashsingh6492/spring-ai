package com.ai.spring.demo.service;

import com.ai.spring.demo.model.Answer;
import com.ai.spring.demo.model.GetCapitalRequest;
import com.ai.spring.demo.model.GetCapitalResponse;
import com.ai.spring.demo.model.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {
    private final ChatModel chatModel;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-prompt_1.st")
    private Resource getCapitalPrompt1;


    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalPromptWithInfo;

    @Autowired
    ObjectMapper objectMapper;

    public OpenAIServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getContent();
    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapital(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(
                Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatResponse response = chatModel.call(prompt);

        System.out.println(response.getResult().getOutput().getContent());
        String responseString;
        try {
            JsonNode jsonNode = objectMapper.readTree(response.getResult().getOutput().getContent());
            responseString = jsonNode.get("answer").asText();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Answer(responseString);
    }


    @Override
    public GetCapitalResponse getCapital1(GetCapitalRequest getCapitalRequest) {
         //it actually creates a JSON schema.
        BeanOutputConverter<GetCapitalResponse> converter = new BeanOutputConverter<>(GetCapitalResponse.class);
        String format = converter.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(),
                "format", format));

        ChatResponse response = chatModel.call(prompt);
//information that basically is going to take the JSON that's being returned
//by OpenAI and bind it to that get capital response record.
        return converter.convert(response.getResult().getOutput().getContent());
    }


    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptWithInfo);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

}
