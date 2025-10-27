package com.workouttracker.workouttracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workouttracker.workouttracker.model.Workout;
import com.workouttracker.workouttracker.service.WorkoutService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/workout")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @GetMapping("/getWorkouts")
    public ResponseEntity<?> getWorkouts(@RequestParam Long userId) {
        List<Workout> workouts = workoutService.getAllWorkouts(userId);

        if (workouts == null || workouts.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "No workouts created for this user"));
        }

        return ResponseEntity.ok(workouts);
    }

    @PostMapping("/createWorkout")
    public ResponseEntity<?> createWorkout(@RequestParam Long userId) {
        List<Workout> workouts = workoutService.getAllWorkouts(userId);

        if (workouts == null || workouts.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "No workouts created for this user"));
        }

        return ResponseEntity.ok(workouts);
    }
    
    
}
