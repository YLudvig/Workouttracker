package com.workouttracker.workouttracker.websocket;

import java.util.Map;

public class SessionEvent {
    public String sessionCode; 
    public Long actorUserId; 
    public String event; 
    public Map<String, Object> payload; 
}
