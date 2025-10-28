package com.workouttracker.workouttracker.websocket;

import java.util.ArrayList;
import java.util.List;

import com.workouttracker.workouttracker.model.Exercise;

// Model för WS
// Detta är vad vi skickar över websocket för att hålla koll på övningarna och hur det går
// och vilka som deltar i passet 
// Detta kommer inte att sparas i sig utan skickas över ws och sedan så sparar vi en workout per deltagare efter passet är klart
public class WorkoutSession {
    
    // Denna fungerar som våran lobbykod eller dylikt alltså hur vi identifierar vilken lobby som vi är i 
    private String sessionId;

    // templateId för användarens template
    private Long templateId; 

    // Namnet på vår workout
    private String templateName; 

    // Lista över övningar 
    private List<Exercise> exercises; 

    // Lista över deltagare i träningssessionen
    private List<Long> participantsIds;

    

    public WorkoutSession(String sessionId, Long templateId, String templateName, List<Exercise> exercises,
            Long hostId) {
        this.sessionId = sessionId;
        this.templateId = templateId;
        this.templateName = templateName;
        this.exercises = exercises;
        // Lägger automatiskt in våran hosts Id i listan av deltagare
        this.participantsIds = new ArrayList<>();
        this.participantsIds.add(hostId);
    }

    // Metod för att lägga till deltagare givet att de inte redan är i listan av deltagare 
    public void addPartcipant(Long userId){
        if (!participantsIds.contains(userId)){
            participantsIds.add(userId);
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String workoutName) {
        this.templateName = workoutName;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Long> getParticipantsIds() {
        return participantsIds;
    }

    public void setParticipantsIds(List<Long> participantsIds) {
        this.participantsIds = participantsIds;
    } 

    

}
