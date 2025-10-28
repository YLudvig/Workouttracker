package com.workouttracker.workouttracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.workouttracker.workouttracker.model.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long>{

    @Query(value = """
            SELECT * 
            FROM workout
            WHERE user_id = :userId
            """, nativeQuery = true)
    List<Workout> findAllByUserId(Long userId);

    @Query(value = """
            SELECT * 
            FROM workout 
            WHERE workout_template_id = :templateId
            """, nativeQuery = true)
    List<Workout> findByTemplateId(Long templateId);
    
}
