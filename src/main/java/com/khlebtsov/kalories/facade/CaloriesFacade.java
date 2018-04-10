package com.khlebtsov.kalories.facade;

import com.khlebtsov.kalories.model.MealModel;
import com.khlebtsov.kalories.service.impl.CaloriesServiceDefault;
import com.khlebtsov.kalories.service.impl.MealServiceDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class CaloriesFacade {

    private final MealServiceDefault mealService;
    private final CaloriesServiceDefault caloriesService;


    @Autowired
    public CaloriesFacade(MealServiceDefault mealService, CaloriesServiceDefault caloriesService) {
        this.mealService = mealService;
        this.caloriesService = caloriesService;
    }

    public Optional<Pair<Long, CaloriesStatus>> getCaloriesByUser(Long userId, LocalDate from, LocalDate to) {
        Optional<Pair<Long, CaloriesStatus>> result = Optional.empty();
        Optional<Long> caloriesForUser = caloriesService.getCaloriesForUser(userId);
        if (caloriesForUser.isPresent()) {
            Long threshold = caloriesForUser.get();
            List<MealModel> mealsByUser = mealService.getMealsByUser(userId, from, to);
            if (!mealsByUser.isEmpty()) {
                long sum = mealsByUser.stream().mapToLong(MealModel::getNumberOfCalories).sum();
                result = Optional.of(Pair.of(sum, determineCaloriesStatus(threshold, sum)));
            }
        }

        return result;

    }

    public Optional<Pair<Long, CaloriesStatus>> getCaloriesByUser(Long userId, LocalDate date) {
        Optional<Pair<Long, CaloriesStatus>> result = Optional.empty();
        Optional<Long> caloriesForUser = caloriesService.getCaloriesForUser(userId);
        if (caloriesForUser.isPresent()) {
            Long threshold = caloriesForUser.get();
            List<MealModel> mealsByUser = mealService.getMealsByUser(userId, date);
            if (!mealsByUser.isEmpty()) {
                long sum = mealsByUser.stream().mapToLong(MealModel::getNumberOfCalories).sum();
                result = Optional.of(Pair.of(sum, determineCaloriesStatus(threshold, sum)));
            }
        }

        return result;
    }

    public Optional<Pair<Long, CaloriesStatus>> getCaloriesByUser(Long userId) {
        Optional<Pair<Long, CaloriesStatus>> result = Optional.empty();
        Optional<Long> caloriesForUser = caloriesService.getCaloriesForUser(userId);
        if (caloriesForUser.isPresent()) {
            Long threshold = caloriesForUser.get();
            List<MealModel> mealsByUser = mealService.getMealsByUser(userId);
            if (!mealsByUser.isEmpty()) {
                long sum = mealsByUser.stream().mapToLong(MealModel::getNumberOfCalories).sum();
                result = Optional.of(Pair.of(sum, determineCaloriesStatus(threshold, sum)));
            }
        }

        return result;
    }

    private CaloriesStatus determineCaloriesStatus(Long threshold, long sum) {
        return sum <= threshold ? CaloriesStatus.OK : CaloriesStatus.WARNING_EXCEED_THRESHOLD;
    }


    public enum CaloriesStatus {
        WARNING_EXCEED_THRESHOLD, OK
    }


}
