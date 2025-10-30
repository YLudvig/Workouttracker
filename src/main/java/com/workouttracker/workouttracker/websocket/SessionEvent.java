package com.workouttracker.workouttracker.websocket;

import java.util.Map;

public class SessionEvent {
    public String sessionCode; 
    public Long actorUserId; 
    public String event; 
    public Map<String, Object> payload;
    
    public String getSessionCode() {
        return sessionCode;
    }
    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }
    public Long getActorUserId() {
        return actorUserId;
    }
    public void setActorUserId(Long actorUserId) {
        this.actorUserId = actorUserId;
    }
    public String getEvent() {
        return event;
    }
    public void setEvent(String event) {
        this.event = event;
    }
    public Map<String, Object> getPayload() {
        return payload;
    }
    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    } 

    
}
