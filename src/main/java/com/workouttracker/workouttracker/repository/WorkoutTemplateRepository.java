package com.workouttracker.workouttracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workouttracker.workouttracker.model.WorkoutTemplate;

public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate, Long>{

    List<WorkoutTemplate> findByUserId(Long userId);
    
}
