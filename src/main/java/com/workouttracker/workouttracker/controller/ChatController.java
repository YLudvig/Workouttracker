package com.workouttracker.workouttracker.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ChatController {
    
    @PostMapping("/chat")
    public String postChat(@RequestBody String prompt) {
        
        return "Hej";
    }
    

}
