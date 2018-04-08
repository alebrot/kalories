package com.khlebtsov.kalories.service;

import com.khlebtsov.kalories.MealRepository;
import com.khlebtsov.kalories.entity.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

@Service
public class MealService {

    private final MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public Collection<Meal> getMeals(LocalDate from, LocalDate to) {
        return mealRepository.getMeals(Date.valueOf(from), Date.valueOf(to));
    }

    public Meal addMeal(Meal meal) {
        return mealRepository.save(meal);
    }
}
