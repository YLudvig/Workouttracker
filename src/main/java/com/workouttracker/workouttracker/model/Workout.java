package com.workouttracker.workouttracker.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "workout")
public class Workout {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long workoutId; 

    private Long userId; 
    private Long workoutTemplateId; 

    private String workoutName; 
    private boolean completed; 
    private LocalDateTime completedAt; 
    
    @ElementCollection
    private List<Exercise> exercises;


    public Workout() {
    }

    public Workout(Long workoutId, Long userId, Long workoutTemplateId, String workoutName, boolean completed,
            LocalDateTime completedAt, List<Exercise> exercises) {
        this.workoutId = workoutId;
        this.userId = userId;
        this.workoutTemplateId = workoutTemplateId;
        this.workoutName = workoutName;
        this.completed = completed;
        this.completedAt = completedAt;
        this.exercises = exercises;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

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

    public Long getWorkoutTemplateId() {
        return workoutTemplateId;
    }

    public void setWorkoutTemplateId(Long workoutTemplateId) {
        this.workoutTemplateId = workoutTemplateId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    } 

    

}
