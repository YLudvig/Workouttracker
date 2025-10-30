package com.workouttracker.workouttracker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workouttracker.workouttracker.DTOs.WorkoutRequest;
import com.workouttracker.workouttracker.model.Exercise;
import com.workouttracker.workouttracker.model.Workout;
import com.workouttracker.workouttracker.repository.ExerciseRepository;
import com.workouttracker.workouttracker.repository.WorkoutRepository;


@Service
public class WorkoutService {

    // Behöver båda repon för att kunna spara en workout då exercises ligger i sin egna tabell 
    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository; 

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

    // Denna metoden nyttjas av våran paymentcontroller för att lägga till workouts till användarna efter de köper en workout 
    public void addBoughtWorkout(Long userId, String priceId) {
        Workout workout = new Workout();
        workout.setUserId(userId);
        workout.setCompleted(false);

        // Har enbart två produkter så designar ett system för att sätta info för just dem 
        if ("price_1SLgj7H45j46nWqxtf1O7bWJ".equals(priceId)){
            workout.setWorkoutName("Premium Push Program");
        } else if ("price_1SLkUFH45j46nWqx0myyaJAo".equals(priceId)) {
            workout.setWorkoutName("Premium Pull Program");
        } else {
            throw new IllegalArgumentException("Okänt priceId" + priceId);
        }

        // Sparar våran workout 
        Workout savedWorkout = workoutRepository.save(workout);

        // Tar nu workoutId från när vi sparade våran workout för att spara ner kopplade övningar till användaren också 
        if ("price_1SLgj7H45j46nWqxtf1O7bWJ".equals(priceId)){
            Exercise benchPress = new Exercise();
            benchPress.setWorkoutId(savedWorkout.getWorkoutId());
            benchPress.setExerciseName("Bench Press");
            benchPress.setWeight(0);
            exerciseRepository.save(benchPress);

            Exercise shoulderPress = new Exercise();
            shoulderPress.setWorkoutId(savedWorkout.getWorkoutId());
            shoulderPress.setExerciseName("Shoulder Press");
            shoulderPress.setWeight(0);
            exerciseRepository.save(shoulderPress);

            Exercise dips = new Exercise();
            dips.setWorkoutId(savedWorkout.getWorkoutId());
            dips.setExerciseName("Dips");
            dips.setWeight(0);
            exerciseRepository.save(dips);

        } else if ("price_1SLkUFH45j46nWqx0myyaJAo".equals(priceId)) {

            Exercise pullups = new Exercise();
            pullups.setWorkoutId(savedWorkout.getWorkoutId());
            pullups.setExerciseName("Pullups");
            pullups.setWeight(0);
            exerciseRepository.save(pullups);

            Exercise deadRow = new Exercise();
            deadRow.setWorkoutId(savedWorkout.getWorkoutId());
            deadRow.setExerciseName("Deadrow");
            deadRow.setWeight(0);
            exerciseRepository.save(deadRow);

            Exercise chinUps = new Exercise();
            chinUps.setWorkoutId(savedWorkout.getWorkoutId());
            chinUps.setExerciseName("Chin ups");
            chinUps.setWeight(0);
            exerciseRepository.save(chinUps);

        }

        System.out.println("Köpt träningspass inlagt för användaren: " + userId);

    }
}
