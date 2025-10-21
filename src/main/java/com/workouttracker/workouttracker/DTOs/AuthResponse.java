package com.workouttracker.workouttracker.DTOs;


public class AuthResponse {

    // Response vid korrekt autentisering osm ger användaren ent oken så att de efter lyckad inloggning har tillåtelse att åtkomma andr aapi calls 
    private String token;

    

    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    } 
    
}
