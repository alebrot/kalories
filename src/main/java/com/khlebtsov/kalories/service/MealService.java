package com.khlebtsov.kalories.service;

import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.model.MealModel;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface MealService {
    @Transactional
    MealModel createOrUpdateMealForUser(Long userId, MealModel mealModel) throws KaloriesException;

    @Transactional
    void deleteMealById(Long id) throws KaloriesException;

    @Transactional
    List<MealModel> getMealsByUser(Long userId);

    @Transactional
    List<MealModel> getMealsByUser(Long userId, LocalDate from, LocalDate to);

    @Transactional
    List<MealModel> getMealsByUser(Long userId, LocalDate date);
}
