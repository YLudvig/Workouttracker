package com.workouttracker.workouttracker.websocket;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebsocketService {

    @Autowired
    private SessionRepository sessionRepository; 

    // Service metod för att skapa en session 
    public Session createSession(Long hostUserId, Long workoutId) {
        
        // genererar vår sessionskod 
        String sessionCode = SessionCodeGenerator.getSessionCode(6);

        System.out.println(sessionCode);

        // Skapar en session med våran hostUserId och vår sessionskod 
        Session session = new Session(sessionCode, hostUserId);
        sessionRepository.save(session);

        return session;
    }

    // Service metod för att joina en session 
    public Session joinSessionByCode(String sessionCode, Long userId){
        // Checkar ifall sessionen man försöker joina faktiskt finns 
        Session session = sessionRepository.findByCode(sessionCode);
        // Avbryter om sessionen inte finns 
        if (session == null) throw new NoSuchElementException("Session inte funnen"); 

        // Finns sessionen så joinar man och läggs till i deltagarlistan 
        session.addParticipant(userId);
        session.setSessionState(SessionStates.JOINED);
        return session; 
    }

    public Session startSession(String sessionCode, Long userId){
         // Checkar ifall sessionen man försöker joina faktiskt finns 
        Session session = sessionRepository.findByCode(sessionCode);
        // Avbryter om sessionen inte finns 
        if (session == null) throw new NoSuchElementException("Session inte funnen"); 
        // Gör så att enbart hosten kan starta
        if (!session.getHostUserId().equals(userId)) throw new IllegalStateException("Enbart hosten kan starta sessionen");
        // Sätter session som startad
        session.setSessionState(SessionStates.STARTED);
        sessionRepository.save(session);
        return session;

    }

    public Session updateSession(String sessionCode, Long userId, Map<String, Object> payload){
        // Checkar ifall sessionen man försöker joina faktiskt finns 
        Session session = sessionRepository.findByCode(sessionCode);
        // Avbryter om sessionen inte finns 
        if (session == null) throw new NoSuchElementException("Session inte funnen"); 

        if (payload != null && payload.containsKey("workoutId")) {
        }
        session.setSessionState(SessionStates.UPDATE);
        return session;

    }

    // Metod för att avsluta sessionen 
    public Session endSession(String sessionCode, Long userId){
        // Checkar ifall sessionen man försöker joina faktiskt finns 
        Session session = sessionRepository.findByCode(sessionCode);
        // Avbryter om sessionen inte finns 
        if (session == null) throw new NoSuchElementException("Session inte funnen"); 
        // Gör så att enbart hosten kan avsluta
        if (!session.getHostUserId().equals(userId)) throw new IllegalStateException("Enbart hosten kan starta sessionen");

        // Sätter sessionen som avslutad och tar bort sessionen
        session.setSessionState(SessionStates.ENDED); 
        sessionRepository.deleteByCode(sessionCode);
        return session;

    }


    
}
