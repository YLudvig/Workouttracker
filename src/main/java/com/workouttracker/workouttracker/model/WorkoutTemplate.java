package com.workouttracker.workouttracker.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table (name = "workout_template")
public class WorkoutTemplate {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long workoutTemplateId; 

    private Long userId; 
    private String templateName; 
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "workout_exercises", joinColumns = @JoinColumn(name = "workout_template_id"))
    private List<Exercise> exercises;


    public WorkoutTemplate() {
    }

    public WorkoutTemplate(Long workoutTemplateId, Long userId, String templateName, boolean completed, List<Exercise> exercises) {
        this.workoutTemplateId = workoutTemplateId;
        this.userId = userId;
        this.templateName = templateName;
        this.exercises = exercises;
    }

    public Long getWorkoutTemplateId() {
        return workoutTemplateId;
    }

    public void setWorkoutTemplateId(Long workoutId) {
        this.workoutTemplateId = workoutId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    } 

    

}
