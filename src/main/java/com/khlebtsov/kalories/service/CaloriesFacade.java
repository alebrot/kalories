package com.khlebtsov.kalories.service;

import com.khlebtsov.kalories.MealModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CaloriesFacade {

    private final MealService mealService;

    @Autowired
    public CaloriesFacade(MealService mealService) {
        this.mealService = mealService;
    }

    public int getCaloriesByUser(Long userId, LocalDate from, LocalDate to) {
        return mealService.getMealsByUser(userId, from, to).stream()
                .mapToInt(MealModel::getNumberOfCalories)
                .sum();
    }

    public int getCaloriesByUser(Long userId, LocalDate date) {
        return mealService.getMealsByUser(userId, date).stream()
                .mapToInt(MealModel::getNumberOfCalories)
                .sum();
    }

    public int getCaloriesByUser(Long userId) {
        return mealService.getMealsByUser(userId).stream()
                .mapToInt(MealModel::getNumberOfCalories)
                .sum();
    }
}
