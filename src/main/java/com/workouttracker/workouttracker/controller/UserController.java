package com.workouttracker.workouttracker.controller;

import com.workouttracker.workouttracker.DTOs.UserDTO;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String email){
        // Checkar att skickade värden inte är tomma eller null och sedan försöker man skapa användaren
        if(username != null && !username.isEmpty() && email != null && !email.isEmpty() && password != null && !password.isEmpty()){
            Optional<UserDTO> optUser = authService.register(username, password, email);
            // Om värden var korrekta så skapas användaren så länge email är unikt i databasen 
            if (optUser.isPresent()){
                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "Lyckades"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "Misslyckades"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "Misslyckades"));
        } 
    }
    
}
