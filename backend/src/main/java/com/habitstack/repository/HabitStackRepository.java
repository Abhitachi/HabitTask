package com.habitstack.repository;

import com.habitstack.model.HabitStack;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitStackRepository extends MongoRepository<HabitStack, String> {
    List<HabitStack> findByNameContainingIgnoreCase(String name);
    Optional<HabitStack> findByName(String name);
    boolean existsByName(String name);
}