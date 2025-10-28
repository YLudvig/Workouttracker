package com.workouttracker.workouttracker.DTOs;


public class AuthResponse {

    // Response vid korrekt autentisering osm ger användaren en token så att de efter lyckad inloggning har tillåtelse att åtkomma andr aapi calls 
    private String token;
    private String username; 
    private Long userId; 
    

    public AuthResponse(String token, String username, Long userId) {
        this.token = token;
        this.username = username;
        this.userId = userId; 
    }

    public AuthResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    } 

    
    
    
}
