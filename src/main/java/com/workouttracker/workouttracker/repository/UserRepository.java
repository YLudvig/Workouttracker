package com.workouttracker.workouttracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workouttracker.workouttracker.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
