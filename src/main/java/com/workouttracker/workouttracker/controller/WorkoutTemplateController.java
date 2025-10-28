package com.workouttracker.workouttracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workouttracker.workouttracker.model.WorkoutTemplate;
import com.workouttracker.workouttracker.service.WorkoutTemplateService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/template")
public class WorkoutTemplateController {

    @Autowired
    private WorkoutTemplateService workoutTemplateService; 


    // Mapping för att hämta alla anvädnarens workouttemplates
    @GetMapping("/getTemplates")
    public List<WorkoutTemplate> getWorkoutTemplates(@RequestParam Long userId) {
        return workoutTemplateService.getWorkoutTemplates(userId);
    }

    // Mapping för att skapa en workout template 
    @PostMapping("/createTemplate")
    public ResponseEntity<?> createWorkoutTemplate(@RequestBody WorkoutTemplate workoutTemplate) {
        return ResponseEntity.ok(workoutTemplateService.createWorkoutTemplate(workoutTemplate));
    }

    
    // Mapping för att hämta alla anvädnarens workouttemplates
    @GetMapping("/getTemplatesByTemplateId")
    public WorkoutTemplate getWorkoutTemplateByWorkoutTemplateId(@RequestParam Long workoutTemplateId) {
        return workoutTemplateService.getWorkoutTemplateByWorkoutTemplateId(workoutTemplateId);
    }
    
    
}
