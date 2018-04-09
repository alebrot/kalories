package com.khlebtsov.kalories.service;

import com.khlebtsov.kalories.MealModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CaloriesFacade {

    @Autowired
    private MealService mealService;

    public int getCalories(LocalDate from, LocalDate to) {
        return mealService.getMeals(from, to).stream()
                .mapToInt(MealModel::getNumberOfCalories)
                .sum();
    }

    public int getCalories(LocalDate date) {
        return mealService.getMeals(date).stream()
                .mapToInt(MealModel::getNumberOfCalories)
                .sum();
    }

    public int getCalories() {
        return mealService.getMeals().stream()
                .mapToInt(MealModel::getNumberOfCalories)
                .sum();
    }
}
