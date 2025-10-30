package com.workouttracker.workouttracker.websocket;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workouttracker.workouttracker.DTOs.WorkoutRequest;
import com.workouttracker.workouttracker.model.Exercise;
import com.workouttracker.workouttracker.model.Workout;
import com.workouttracker.workouttracker.service.WorkoutService;

@Service
public class WebsocketService {

    @Autowired
    private SessionRepository sessionRepository;  
    
    @Autowired 
    private WorkoutService workoutService; 


    // Service metod för att skapa en session 
    public Session createSession(Long hostUserId, Long workoutId) {

        Workout workout = workoutService.getWorkoutByWorkoutId(workoutId); 
        if (workout == null){
            throw new IllegalArgumentException("error med workout" + workoutId);
        }
        

        List<Exercise> exercises = workoutService.getExercisesByWorkoutId(workoutId); 
        
        // genererar vår sessionskod 
        String sessionCode = SessionCodeGenerator.getSessionCode(6);


        // Skapar en session med våran hostUserId och vår sessionskod 
        Session session = new Session(sessionCode, hostUserId);
        session.setWorkout(workout); 
        session.setExercises(exercises); 

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
        // Sätter session som startad
        session.setSessionState(SessionStates.STARTED);
        sessionRepository.save(session);
        return session;

    }

    // Metod för att uppdatera ens övningar 
    public void updateExerciseProgress(String sessionCode, Long userId, Long exerciseId){
        Session session = sessionRepository.findByCode(sessionCode); 
        if (session != null){
            List<Exercise> exercises = session.getExercises(); 
            for (Exercise ex : exercises){
                if (ex.getExerciseId().equals(exerciseId)){
                    ex.setCompleted(true); 
                    break; 
                }
            }
            sessionRepository.save(session); 
        }
    }


    public void endSessionAndSaveWorkouts(String sessionCode){
        Session session = sessionRepository.findByCode(sessionCode); 
        if (session != null){
            for (Long particpantId : session.getParticipants()){
                WorkoutRequest request = new WorkoutRequest(); 
                request.setUserId(particpantId);
                request.setWorkoutName(session.getWorkout().getWorkoutName() + " copy"); 
                request.setCompleted(true); 
                request.setExercises(session.getExercises()); 
                workoutService.createWorkout(request); 
            }
            sessionRepository.deleteByCode(sessionCode); 
        }
    }


   public Session getSession(String sessionCode){
    return sessionRepository.findByCode(sessionCode);
   }


    
}
