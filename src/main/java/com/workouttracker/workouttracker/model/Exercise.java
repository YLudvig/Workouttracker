package com.workouttracker.workouttracker.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Exercise {
    
    private String exerciseName; 
    private double weight;
    private boolean completed;

    public Exercise() {
    }
    
    public Exercise(String exerciseName, double weight, boolean completed) {
        this.exerciseName = exerciseName;
        this.weight = weight;
        this.completed = completed;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }  

}
