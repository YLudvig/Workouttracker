package com.workouttracker.workouttracker.websocket;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

// Tagit mycket inspiration från multiplayer uppgiften bl.a. hur vi nyttjade ConcurrentHashMaps för att lagra flera samtida sessioner 
@Component
public class WorkoutSessionManager {

    // Lista över aktiva sessioner (typ lobbys)
    private Map<String, WorkoutSession> sessions = new ConcurrentHashMap<>();
    
    // Logiken för att skapa en ws session
    public WorkoutSession createSession(WorkoutSession session){
        sessions.put(session.getSessionId(), session);
        return session;
    }

    // Håller alla användare uppdaterade 
    public Optional<WorkoutSession> getSession(String sessionId){
        return Optional.ofNullable(sessions.get(sessionId));
    }

    // Metod för att avsluta en session
    public void endSession(String sessionId){
        sessions.remove(sessionId);
    }

}
