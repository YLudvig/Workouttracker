package com.workouttracker.workouttracker.model;

import java.time.LocalDateTime;

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
    private String workoutName; 
    private boolean completed; 
    private LocalDateTime completedAt; 
    


    public Workout() {
    }

    public Workout(Long workoutId, Long userId, String workoutName, boolean completed,
            LocalDateTime completedAt) {
        this.workoutId = workoutId;
        this.userId = userId;
        this.workoutName = workoutName;
        this.completed = completed;
        this.completedAt = completedAt;
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

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    } 

    

}
