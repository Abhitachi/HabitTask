package com.habitstack.repository;

import com.habitstack.model.ProgressData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgressDataRepository extends MongoRepository<ProgressData, String> {
    Optional<ProgressData> findByStackId(String stackId);
    void deleteByStackId(String stackId);
}