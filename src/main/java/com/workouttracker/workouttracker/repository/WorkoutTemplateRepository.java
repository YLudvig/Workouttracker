package com.workouttracker.workouttracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.workouttracker.workouttracker.model.WorkoutTemplate;

public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate, Long>{

    List<WorkoutTemplate> findByUserId(Long userId);

    @Query(value = """
            SELECT * 
            FROM workout_template
            WHERE workout_template_id = :templateId
            """, nativeQuery = true)
    WorkoutTemplate getByTemplateId(Long templateId);
    
}
