package com.workouttracker.workouttracker.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/workouttracker")
public class ChatController {
    
    @PostMapping("/chat")
    public String postChat(@RequestBody String prompt) {
        
        return "Hej";
    }
    

}
