package com.khlebtsov.kalories.service;

import com.khlebtsov.kalories.MealRepository;
import com.khlebtsov.kalories.entity.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MealService {

    private final MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> getMeals(LocalDate from, LocalDate to) {
        return mealRepository.getMeals(Date.valueOf(from), Date.valueOf(to));
    }

    public List<Meal> getMeals(LocalDate from) {
        return mealRepository.getMeals(Date.valueOf(from));
    }

    public List<Meal> getMeals() {
        return StreamSupport.stream(mealRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Meal addMeal(Meal meal) {
        return mealRepository.save(meal);
    }
}
