package com.workouttracker.workouttracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workouttracker.workouttracker.model.Workout;
import com.workouttracker.workouttracker.repository.WorkoutRepository;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    public List<Workout> getAllWorkouts(Long userId) {
        return workoutRepository.findAllByUserId(userId);
    }
    
    
}
