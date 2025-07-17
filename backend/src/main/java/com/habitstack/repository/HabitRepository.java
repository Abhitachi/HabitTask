package com.habitstack.repository;

import com.habitstack.model.Habit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends MongoRepository<Habit, String> {
    List<Habit> findByCategory(String category);
    List<Habit> findByNameContainingIgnoreCase(String name);
    List<Habit> findByCategoryAndNameContainingIgnoreCase(String category, String name);
    Optional<Habit> findByName(String name);
    boolean existsByName(String name);
}