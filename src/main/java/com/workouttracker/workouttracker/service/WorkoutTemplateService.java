package com.workouttracker.workouttracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workouttracker.workouttracker.model.WorkoutTemplate;
import com.workouttracker.workouttracker.repository.WorkoutTemplateRepository;

@Service
public class WorkoutTemplateService {

    @Autowired
    private WorkoutTemplateRepository workoutTemplateRepository;


    // Metod för att hämta alla användarens workout templates för att visa dem på hemsidan
    public List<WorkoutTemplate> getWorkoutTemplates(Long userId) {
        return workoutTemplateRepository.findByUserId(userId);
    }


    // Metod för att skapa ny workout template 
    public WorkoutTemplate createWorkoutTemplate(WorkoutTemplate workoutTemplate){
        return workoutTemplateRepository.save(workoutTemplate);
    }


    public WorkoutTemplate getWorkoutTemplateByWorkoutTemplateId(Long workoutTemplateId){
        return workoutTemplateRepository.findByWorkoutTemplateId(workoutTemplateId);
    }
    
}
