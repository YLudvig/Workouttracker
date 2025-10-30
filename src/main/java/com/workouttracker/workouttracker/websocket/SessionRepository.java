package com.workouttracker.workouttracker.websocket;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

// Sett att lagra våra session utan att faktiskt spara dem i databas
@Service
public class SessionRepository {

    private final ConcurrentHashMap<String, Session> sessionsBySessionCode = new ConcurrentHashMap<>(); 

    // Metod för att hitta en session baserad på sessionskod (behövs för att joina)
    public Session findByCode(String sessionCode) {
       if (sessionCode == null) return null; 
       return sessionsBySessionCode.get(sessionCode);
    }

    // Metod för att spara session (temporärt)
    public void save(Session session) {
       if (session == null || session.getSessionCode() == null){
        throw new IllegalArgumentException("Session och eller sessionkod var tomma/null");
       } 
       sessionsBySessionCode.put(session.getSessionCode(), session);
    }
    
    // Deletear sessionen (nyttjas när man avslutar sin session)
    public boolean deleteByCode(String sessionCode){
        if (sessionCode == null) return false; 
        if (findByCode(sessionCode) == null) return false; 
        return sessionsBySessionCode.remove(sessionCode) != null;
    }

}
