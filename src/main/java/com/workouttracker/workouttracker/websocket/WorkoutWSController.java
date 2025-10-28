package com.workouttracker.workouttracker.websocket;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.workouttracker.workouttracker.model.Exercise;
import com.workouttracker.workouttracker.model.WorkoutTemplate;
import com.workouttracker.workouttracker.service.WorkoutService;
import com.workouttracker.workouttracker.service.WorkoutTemplateService;

// Controller för våran WS genom denna så hanterar vi start av workout, 
// updateringar under workouts (när man avslutar set) och avslutning av workout
@Controller
public class WorkoutWSController {
   
    @Autowired
    private SimpMessagingTemplate messagingTemplate; 

    // Funktionellt sett service filen för min websocket
    @Autowired 
    private WorkoutSessionManager sessionManager; 

    // Servicen för databasinteraktion för våra workouts, alltså för att spara vid avslutad workout 
    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private WorkoutTemplateService templateService;


    // Mapping för att starta våran workout med ws
    @MessageMapping("/session/start")
    public void startWorkout(Map<String, Object> payload, SimpMessageHeaderAccessor headerAccessor){
        // Hämtar id värdena för att skapa session
        Long userId = Long.valueOf(payload.get("userId").toString());
        Long templateId = Long.valueOf(payload.get("templateId").toString());

        WorkoutTemplate template = templateService.getWorkoutTemplate(templateId);

        // Skapar sessionsidt 
        String sessionId = SessionCodeGenerator.getSessionCode(6);

        System.out.println(sessionId);

        // Skapar sessionen 
        WorkoutSession session = new WorkoutSession(sessionId, templateId, template.getTemplateName(), template.getExercises(), userId);
 
        // Kör vår metod för att starta en session
        sessionManager.createSession(session);

        System.out.println("Header sessionId" + headerAccessor.getSessionId());
        System.out.println("Active users" + messagingTemplate.getUserDestinationPrefix());

        Principal user = headerAccessor.getUser();
        if (user != null){
            String username = user.getName(); 
            System.out.println("Skickar till användaren: " + username);
            System.out.println(user.getName());
            messagingTemplate.convertAndSendToUser(username, "/queue/session-started", Map.of("sessionId", sessionId, "sessionData", session));
        } else {
            System.out.println("Blev fel med principal");
        }   
       
        // Startar vår session och börjar skicka meddelanden
        messagingTemplate.convertAndSend("/topic/session/" + sessionId, session);
    }

    // Mapping för att joina en workout 
    @MessageMapping("/session/join")
    public void joinSession(Map<String, Object> payload){
        Long userId = Long.valueOf(payload.get("userId").toString());
        String sessionId = payload.get("sessionId").toString();

        WorkoutSession session = sessionManager.getSession(sessionId).orElseThrow(() -> new RuntimeException("Session inte funnen"));

        // Lägger till användaren i listan av användare i sessionen 
        session.addPartcipant(userId);

        messagingTemplate.convertAndSend("/topic/session/" + sessionId, session);
    }

    // Mapping för att updatera en workout (typ när man avslutar ett set)
    @MessageMapping("/session/updateExercise")
    public void updateExercise(Map<String, Object> payload){
        String sessionId = payload.get("sessionId").toString();
        Map<String, Object> exerciseData = (Map<String, Object>) payload.get("exercise");

        WorkoutSession session = sessionManager.getSession(sessionId).orElseThrow(() -> new RuntimeException("Session inte funnen"));

        String exerciseName = exerciseData.get("exerciseName").toString();
        double weight = Double.parseDouble(exerciseData.get("weight").toString());
        boolean completed = Boolean.parseBoolean(exerciseData.get("completed").toString());

        // Loopar igenom övningar tills man når rätt övning sedan sätter man vikten och completed på den och sedan går man vidare
        for (Exercise ex : session.getExercises()) {
            if (ex.getExerciseName().equalsIgnoreCase(exerciseName)){
                ex.setWeight(weight);
                ex.setCompleted(completed);
                break; 
            }
        }

        // Skicka ut updaterad session till användarna 
        messagingTemplate.convertAndSend("/topic/session/" + sessionId, session);
    }

    // Mapping för att avsluta en workout
    @MessageMapping("/session/end")
    public void endSession(Map<String, Object> payload){
        String sessionId = payload.get("sessionId").toString();

        WorkoutSession session = sessionManager.getSession(sessionId).orElseThrow(() -> new RuntimeException("Session inte funnen"));

        workoutService.saveWorkoutForParticipants(session);
        sessionManager.endSession(sessionId);

        messagingTemplate.convertAndSend("/topic/session/" + sessionId, "Session avslutad för alla användare");

    }


}
