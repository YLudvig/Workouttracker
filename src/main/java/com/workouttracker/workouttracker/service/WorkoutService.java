package com.workouttracker.workouttracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.workouttracker.workouttracker.model.Workout;
import com.workouttracker.workouttracker.repository.WorkoutRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    public List<Workout> getAllWorkouts(Long userId) {
        return workoutRepository.findAllByUserId(userId);
    }
    
    
}
