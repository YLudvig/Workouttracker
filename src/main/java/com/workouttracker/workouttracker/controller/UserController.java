package com.workouttracker.workouttracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workouttracker.workouttracker.DTOs.AuthRequest;
import com.workouttracker.workouttracker.DTOs.AuthResponse;
import com.workouttracker.workouttracker.Util.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private AuthService authService; 

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String email){
        return authService.register(username, password, email);
    }
    
    
    
}
