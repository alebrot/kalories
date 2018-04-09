package com.khlebtsov.kalories.mapper;

import com.khlebtsov.kalories.MealModel;
import com.khlebtsov.kalories.dto.MealDto;
import org.springframework.stereotype.Component;

@Component
public class MealDtoModelMapper implements Mapper<MealDto, MealModel> {

    @Override
    public MealModel map(MealDto mealDto) {
        return new MealModel(mealDto.getText(), mealDto.getNumberOfCalories());
    }
}
