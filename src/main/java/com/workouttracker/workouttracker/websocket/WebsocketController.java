package com.workouttracker.workouttracker.websocket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {

    @Autowired
    private WebsocketService websocketService;

    @Autowired 
    private SimpMessagingTemplate simpMessaging; 

    
    // Mapping för att skapa en session 
    @MessageMapping("/session-create")
    @SendToUser("/queue/create-response")
    public CreateSessionResponse createSession(CreateSessionRequest request){
        // Skapar en session genom att skicka med hostUserId och skapar en session med den personen som host
        // Skickar även med en workoutId för den workouten de har valt att starta 
        Session workoutSession = websocketService.createSession(request.hostUserId, request.workoutId); 

        // Skapar en sessionEvent att returnera till topicen de subscribeat till 
        SessionEvent sessionEvent = new SessionEvent(); 
        sessionEvent.sessionCode = workoutSession.getSessionCode();
        sessionEvent.actorUserId = request.hostUserId;
        sessionEvent.event = "SESSION_CREATED";
        sessionEvent.payload = Map.of(
            "participants", workoutSession.getParticipants(), 
            "sessionState", workoutSession.getSessionState().name(),
            "workout", workoutSession.getWorkout(),
            "exercises", workoutSession.getExercises()
            );

        // Skickar info till topicen de subscribeat till 
        simpMessaging.convertAndSend("/topic/session." + workoutSession.getSessionCode(), sessionEvent);

        // Skickar sessionskoden till skaparen för att visa den så att de kan ge den till vänner/deltagare
        CreateSessionResponse response = new CreateSessionResponse(); 
        response.sessionId = workoutSession.getId(); 
        response.sessionCode = workoutSession.getSessionCode(); 
        return response; 
    }

    // Mapping för att joina en session 
    @MessageMapping("/session-join")
    public void joinSession(JoinSessionRequest request){
        Session workoutSession = websocketService.joinSessionByCode(request.sessionCode, request.userId); 
        SessionEvent sessionEvent = new SessionEvent();

        sessionEvent.sessionCode = workoutSession.getSessionCode();
        sessionEvent.actorUserId = request.userId;
        sessionEvent.event = "USER_JOINED";
        sessionEvent.payload = Map.of("participants", workoutSession.getParticipants(), "sessionState", workoutSession.getSessionState().name());
        
        // Returnerar en snapshot av vad som hänt och vem som joinat
        simpMessaging.convertAndSend("/topic/session." + workoutSession.getSessionCode(), sessionEvent);

        SessionEvent toJoiningUser = new SessionEvent(); 
        toJoiningUser.sessionCode = workoutSession.getSessionCode();
        toJoiningUser.actorUserId = request.userId; 
        toJoiningUser.event = "SESSION_SYNC";
        toJoiningUser.payload = Map.of(
            "participants", workoutSession.getParticipants(), 
            "sessionState", workoutSession.getSessionState().name(),
            "workout", workoutSession.getWorkout(),
            "exercises", workoutSession.getExercises()
            );

        simpMessaging.convertAndSendToUser(request.userId.toString(), "/queue/session-sync", toJoiningUser);
    }

    // Mapping för att starta en session 
    @MessageMapping("/session-start")
    public void startSession(SessionEvent request){
        Session workoutSession = websocketService.startSession(request.sessionCode, request.actorUserId); 
        SessionEvent sessionEvent = new SessionEvent();

        sessionEvent.sessionCode = workoutSession.getSessionCode();
        sessionEvent.actorUserId = request.actorUserId;
        sessionEvent.event = "SESSION_STARTED";
        sessionEvent.payload = Map.of(
            "participants", workoutSession.getParticipants(), 
            "sessionState", workoutSession.getSessionState().name(),
            "workout", workoutSession.getWorkout(),
            "exercises", workoutSession.getExercises()
            );
        
        // Returnerar en snapshot av vad som hänt och vem som joinat
        simpMessaging.convertAndSend("/topic/session." + workoutSession.getSessionCode(), sessionEvent);
    }
    
    // Mapping för sessionsuppdatering 
    @MessageMapping("/session-update")
    public void updateSession(SessionEvent request){

        Session workoutSession = websocketService.getSession(request.sessionCode); 

        // Om ingen session avbryt 
        if (workoutSession == null) return; 

        if (request.payload != null && request.payload.containsKey("exerciseId")){
            Long exerciseId = Long.valueOf(request.payload.get("exerciseId").toString()); 
            websocketService.updateExerciseProgress(workoutSession.getSessionCode(), request.actorUserId, exerciseId);
        }

        SessionEvent sessionEvent = new SessionEvent();

        sessionEvent.sessionCode = workoutSession.getSessionCode();
        sessionEvent.actorUserId = request.actorUserId;
        sessionEvent.event = "SESSION_UPDATE";
        sessionEvent.payload = Map.of(
            "participants",workoutSession.getParticipants(), 
            "sessionState",workoutSession.getSessionState().name(),
            "workout", workoutSession.getWorkout(),
            "exercises", workoutSession.getExercises()
            );
        
        // Returnerar en snapshot av vad som hänt och vem som joinat
        simpMessaging.convertAndSend("/topic/session." + workoutSession.getSessionCode(), sessionEvent);
    }

     // Mapping för att starta en session 
    @MessageMapping("/session-end")
    public void endSession(SessionEvent request){

        Session workoutSession = websocketService.getSession(request.sessionCode); 
        websocketService.endSessionAndSaveWorkouts(request.sessionCode); 
        SessionEvent sessionEvent = new SessionEvent();

        sessionEvent.sessionCode = request.getSessionCode();
        sessionEvent.actorUserId = request.actorUserId;
        sessionEvent.event = "SESSION_ENDED";
        sessionEvent.payload = Map.of(
            "sessionState", "ENDED",
            "workout", workoutSession.getWorkout(),
            "exercises", workoutSession.getExercises()
            );
        
        // Returnerar en snapshot av vad som hänt och vem som joinat
        simpMessaging.convertAndSend("/topic/session." + request.getSessionCode(), sessionEvent);
    }

}
