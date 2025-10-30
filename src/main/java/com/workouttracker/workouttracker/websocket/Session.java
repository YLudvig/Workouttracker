package com.workouttracker.workouttracker.websocket;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.workouttracker.workouttracker.model.Exercise;
import com.workouttracker.workouttracker.model.Workout;

// Typar upp hur en session ser ut och vad den skickar 
public class Session {
    
    private final String id; 
    private final String sessionCode; 
    private final Long hostUserId; 
    private final Instant creationTime; 
    private final List<Long> participants = new ArrayList<>(); 
    private SessionStates sessionState; 
    private Workout workout;
    private List<Exercise> exercises;

    public Session(String sessionCode, Long hostUserId) {
        this.id = UUID.randomUUID().toString();
        this.sessionCode = sessionCode;
        this.hostUserId = hostUserId;
        this.creationTime = Instant.now();
        this.participants.add(hostUserId);
        this.sessionState = SessionStates.CREATED;
    }

    public String getId() {
        return id;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public Long getHostUserId() {
        return hostUserId;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public List<Long> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    // Metod för att lägga till deltagare i deltagarlistan 
    public boolean addParticipant(Long userId){
        if (!participants.contains(userId)) {
            participants.add(userId);
            return true; 
        }
        return false; 
    }

    public boolean removeParticipant(Long userId){
        return participants.remove(userId);
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public SessionStates getSessionState() {
        return sessionState;
    }

    public void setSessionState(SessionStates sessionState) {
        this.sessionState = sessionState;
    }

    

}
