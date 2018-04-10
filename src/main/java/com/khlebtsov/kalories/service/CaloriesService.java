package com.khlebtsov.kalories.service;

import com.khlebtsov.kalories.CaloriesPerUserRepository;
import com.khlebtsov.kalories.entity.CaloriesPerUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CaloriesService {

    private final CaloriesPerUserRepository caloriesPerUserRepository;

    @Autowired
    public CaloriesService(CaloriesPerUserRepository caloriesPerUserRepository) {
        this.caloriesPerUserRepository = caloriesPerUserRepository;
    }

    public Optional<Long> getCaloriesForUser(Long userId) {
        return caloriesPerUserRepository.findByUserId(userId).map(CaloriesPerUserEntity::getNumberOfCalories);
    }
}
