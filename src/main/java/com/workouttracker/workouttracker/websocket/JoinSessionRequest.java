package com.workouttracker.workouttracker.websocket;

// För att joina en session så behöver det skickas in vem som ska joina och vilken session man vill joina 
public class JoinSessionRequest {
    public String sessionCode; 
    public Long userId; 
}
