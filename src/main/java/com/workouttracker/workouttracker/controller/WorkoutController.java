package com.workouttracker.workouttracker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workouttracker.workouttracker.DTOs.WorkoutRequest;
import com.workouttracker.workouttracker.model.Workout;
import com.workouttracker.workouttracker.service.WorkoutService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/workout")
public class WorkoutController {
    
    @Autowired
    private WorkoutService workoutService; 


    // Postmapping för att spara en workout
    @PostMapping("/createWorkout")
    public Map<String, Object> createWorkout(@RequestBody WorkoutRequest request) {
        Workout saved = workoutService.createWorkout(request);
        return Map.of("message", "Workout skapad", "med namnet", saved.getWorkoutName());
    }
    

    // Getmapping för att hämta alla en användares workouts 
    @GetMapping("/getUsersWorkouts")
    public List<Map<String, Object>> getUsersWorkouts(@RequestParam Long userId){
        // Hämtar alla workouts
        List<Workout> workouts = workoutService.getAllWorkoutsByUserId(userId);
        // Loopar igenom alla workouts och fetchar deras övningar från exercise tabellen 
        // Detta kombineras sedan till våran respons till frontend
        List<Map<String, Object>> response = new ArrayList<>(); 
        for (Workout workout : workouts){
            response.add(Map.of(
                "workout", workout, 
                "exercises", workoutService.getExercisesByWorkoutId(workout.getWorkoutId())
            ));
        }
        return response;
    }

    // Getmapping för att hämta en workout och dess övningar baserat på workoutId 
    // Behövs bl.a. på workoutSidan 
    @GetMapping("/getWorkout")
    public Map<String, Object> getWorkoutById(@RequestParam Long workoutId) {
        Workout workout = workoutService.getWorkoutByWorkoutId(workoutId);
        return Map.of("workout", workout, "exercises", workoutService.getExercisesByWorkoutId(workoutId));
    }

    // Mapping för att deletea en workout 
    @DeleteMapping("/deleteWorkout")
    public Map<String, String> deleteWorkout(@RequestParam Long workoutId){
        workoutService.deleteWorkout(workoutId);
        return Map.of("message", "Workout och exercises deletat");
    }
    

}
