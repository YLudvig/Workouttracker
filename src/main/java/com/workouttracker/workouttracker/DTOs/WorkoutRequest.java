package com.workouttracker.workouttracker.DTOs;

import java.util.List;

import com.workouttracker.workouttracker.model.Exercise;

// Denna filen typar upp hur en request för att skapa en workout ska se ut
public class WorkoutRequest {
    private Long userId; 
    private String workoutName; 
    private boolean completed; 
    private List<Exercise> exercises;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getWorkoutName() {
        return workoutName;
    }
    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public List<Exercise> getExercises() {
        return exercises;
    }
    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    } 


    
}
