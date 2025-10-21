package com.workouttracker.workouttracker.AI;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/workouttracker")
public class ChatController {

    @Autowired
    private ChatService chatService;
    

    @PostMapping("/chat")
    public String postChat(@RequestBody String prompt) {

        ChatResponse response = chatService.sendChatResponse(prompt);
        
        return response.getChoices().get(0).getMessage().getContent();
    }
    

}
