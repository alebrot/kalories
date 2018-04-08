package com.khlebtsov.kalories.mapper;

import com.khlebtsov.kalories.dto.AddMealRequest;
import com.khlebtsov.kalories.dto.MealDto;
import com.khlebtsov.kalories.entity.Meal;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DtoModelMapper implements Function<MealDto, Meal> {
    /**
     * Applies this function to the given argument.
     *
     * @param mealDto the function argument
     * @return the function result
     */
    @Override
    public Meal apply(MealDto mealDto) {

        Meal meal = new Meal();
        meal.setText(mealDto.getText());
        meal.setNumberOfCalories(mealDto.getNumberOfCalories());
        return meal;
    }
}
