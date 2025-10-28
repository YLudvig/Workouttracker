package com.workouttracker.workouttracker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workouttracker.workouttracker.model.Workout;
import com.workouttracker.workouttracker.repository.UserRepository;
import com.workouttracker.workouttracker.repository.WorkoutRepository;
import com.workouttracker.workouttracker.websocket.WorkoutSession;

@Service
public class WorkoutService {

    private final UserRepository userRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    WorkoutService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Service metod för att spara en workout i databasen, viktigt för att låta användare default använda samma vikter som förra gången 
    // Använder websocket för att ta en template -> genomföra den med websocket -> spara den som en workout i vår databas
    public void saveWorkoutForParticipants(WorkoutSession session){
        for (Long userId : session.getParticipantsIds()) {
            Workout workout = new Workout();
            workout.setUserId(userId);
            workout.setWorkoutName(session.getTemplateName());
            workout.setExercises(session.getExercises());
            workout.setCompleted(true);

            workoutRepository.save(workout);
        }
    }
    
}
