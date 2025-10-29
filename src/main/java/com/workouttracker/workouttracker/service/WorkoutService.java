package com.workouttracker.workouttracker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workouttracker.workouttracker.DTOs.WorkoutRequest;
import com.workouttracker.workouttracker.model.Exercise;
import com.workouttracker.workouttracker.model.Workout;
import com.workouttracker.workouttracker.repository.ExerciseRepository;
import com.workouttracker.workouttracker.repository.UserRepository;
import com.workouttracker.workouttracker.repository.WorkoutRepository;
import com.workouttracker.workouttracker.websocket.WorkoutSession;

@Service
public class WorkoutService {

    // Behöver båda repon för att kunna spara en workout då exercises ligger i sin egna tabell 
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository; 

    public WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository) {
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }

    // Metod för att spara en workout och dess exercises (exercise är sin egna tabell som har en relation via workoutId)
    public Workout createWorkout(WorkoutRequest request){
        // Skapar en ny workout och setter dess värden med värden från requesten innan vi sparar den 
        Workout workout = new Workout();
        workout.setUserId(request.getUserId());
        workout.setWorkoutName(request.getWorkoutName());
        workout.setCompleted(request.isCompleted());
        // Om workout är completed alltså genomförd så vill vi sätta en timestamp så att vi håller koll på när workouts genomförts 
        if (request.isCompleted()){
            workout.setCompletedAt(LocalDateTime.now());
        }

        // Sparar vår workout genom repot 
        Workout saveWorkout = workoutRepository.save(workout);

        // Om våran request innehöll övningar så loopar vi igenom dem och sparar dem i databasen 
        if (request.getExercises() != null){
            for (Exercise e : request.getExercises()){
                e.setWorkoutId(saveWorkout.getWorkoutId());
                exerciseRepository.save(e);
            }
        }
        return saveWorkout;
    }

    // Hämta alla workouts för användaren 
    public List<Workout> getAllWorkoutsByUserId(Long userId){
        return workoutRepository.findAllByUserId(userId);
    }

    // Hämta specific workout
    public Workout getWorkoutByWorkoutId(Long workoutId){
        return workoutRepository.findByWorkoutId(workoutId);
    }

    // Hämta alla övningar för en workout från exercise tabellen 
    public List<Exercise> getExercisesByWorkoutId(Long workoutId){
        return exerciseRepository.findByWorkoutId(workoutId);
    }

    // Deletea workout (behöver/bör även deletea alla kopplade övningar)
    @Transactional
    public void deleteWorkout(Long workoutId){
        exerciseRepository.deleteByWorkoutId(workoutId);
        workoutRepository.deleteById(workoutId);
    }
}
