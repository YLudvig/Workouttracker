package com.workouttracker.workouttracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workouttracker.workouttracker.model.Exercise;
import java.util.List;


public interface ExerciseRepository extends JpaRepository<Exercise, Long>{
    List<Exercise> findByWorkoutId(Long workoutId);
    void deleteByWorkoutId(Long workoutId);
}
