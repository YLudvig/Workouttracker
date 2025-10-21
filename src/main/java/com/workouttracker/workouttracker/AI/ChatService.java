package com.workouttracker.workouttracker.AI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatService {

    @Value("${openai.api.url}")
    String apiUrl;

    private final RestTemplate restTemplate;

    public ChatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public ChatResponse sendChatResponse(String prompt){

        ChatRequest chatRequest = new ChatRequest("gpt-4o", prompt, 1);
        ChatResponse chatResponse = restTemplate.postForObject(apiUrl, chatRequest, ChatResponse.class);

        return chatResponse;
    }

}
