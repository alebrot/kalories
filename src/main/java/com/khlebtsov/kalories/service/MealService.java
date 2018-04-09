package com.khlebtsov.kalories.service;

import com.khlebtsov.kalories.MealRepository;
import com.khlebtsov.kalories.entity.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MealService {

    private final MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> getMeals(LocalDateTime from, LocalDateTime to) {
        return mealRepository.getMeals(Timestamp.valueOf(from), Timestamp.valueOf(to));
    }

    public List<Meal> getMeals(LocalDate date) {
        return mealRepository.getMeals(Date.valueOf(date));
    }

    public List<Meal> getMeals() {
        return StreamSupport.stream(mealRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Meal addMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public void deleteMeal(long id) {
        mealRepository.deleteById(id);
    }

    public void deleteMeal(Meal meal) {
        mealRepository.delete(meal);
    }


    public Optional<Meal> getMeal(long id) {
        return mealRepository.findById(id);
    }
}
