package com.khlebtsov.kalories.layers.facade.impl;

import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.model.MealModel;
import com.khlebtsov.kalories.layers.service.impl.CaloriesServiceDefault;
import com.khlebtsov.kalories.layers.service.impl.MealServiceDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class CaloriesFacadeDefault implements com.khlebtsov.kalories.layers.facade.CaloriesFacade {

    private final MealServiceDefault mealService;
    private final CaloriesServiceDefault caloriesService;


    @Autowired
    public CaloriesFacadeDefault(MealServiceDefault mealService, CaloriesServiceDefault caloriesService) {
        this.mealService = mealService;
        this.caloriesService = caloriesService;
    }

    @Override
    public Optional<Pair<Long, CaloriesStatus>> getCaloriesByUser(Long userId, LocalDate from, LocalDate to) throws KaloriesException {
        Optional<Pair<Long, CaloriesStatus>> result = Optional.empty();
        Long threshold = caloriesService.getCaloriesForUser(userId);
        List<MealModel> mealsByUser = mealService.getMealsByUser(userId, from, to);
        if (!mealsByUser.isEmpty()) {
            long sum = mealsByUser.stream().mapToLong(MealModel::getNumberOfCalories).sum();
            result = Optional.of(Pair.of(sum, determineCaloriesStatus(threshold, sum)));
        }

        return result;

    }

    @Override
    public Optional<Pair<Long, CaloriesStatus>> getCaloriesByUser(Long userId, LocalDate date) throws KaloriesException {
        Optional<Pair<Long, CaloriesStatus>> result = Optional.empty();
        Long threshold = caloriesService.getCaloriesForUser(userId);
        List<MealModel> mealsByUser = mealService.getMealsByUser(userId, date);
        if (!mealsByUser.isEmpty()) {
            long sum = mealsByUser.stream().mapToLong(MealModel::getNumberOfCalories).sum();
            result = Optional.of(Pair.of(sum, determineCaloriesStatus(threshold, sum)));
        }

        return result;
    }

    @Override
    public Optional<Pair<Long, CaloriesStatus>> getCaloriesByUser(Long userId) throws KaloriesException {
        Optional<Pair<Long, CaloriesStatus>> result = Optional.empty();
        Long threshold = caloriesService.getCaloriesForUser(userId);
        List<MealModel> mealsByUser = mealService.getMealsByUser(userId);
        if (!mealsByUser.isEmpty()) {
            long sum = mealsByUser.stream().mapToLong(MealModel::getNumberOfCalories).sum();
            result = Optional.of(Pair.of(sum, determineCaloriesStatus(threshold, sum)));
        }

        return result;
    }

    @Override
    public Long getCaloriesThresholdForUser(Long userId) throws KaloriesException {
        return caloriesService.getCaloriesForUser(userId);
    }

    private CaloriesStatus determineCaloriesStatus(Long threshold, long sum) {
        return sum <= threshold ? CaloriesStatus.OK : CaloriesStatus.WARNING_EXCEED_THRESHOLD;
    }


    public enum CaloriesStatus {
        WARNING_EXCEED_THRESHOLD, OK
    }


}
