package com.workouttracker.workouttracker.AI;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// Skapar ett template för hur man ska skicka requests till openai 
@Configuration
public class OpenAIRestTemplate {

    @Value("${openai.api.key}")
    private String apiKey; 

    @Bean
    @Qualifier("openAiRestTemplate")

    public RestTemplate openAiRestTemplate(){

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((request, body, execution) ->{
            request.getHeaders().add("Authorization", "Bearer " + apiKey);
            return execution.execute(request, body);

        });

        return restTemplate;
    }

    
}
