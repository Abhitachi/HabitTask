package com.habitstack.repository;

import com.habitstack.model.HabitCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabitCategoryRepository extends MongoRepository<HabitCategory, String> {
    Optional<HabitCategory> findByName(String name);
    boolean existsByName(String name);
}