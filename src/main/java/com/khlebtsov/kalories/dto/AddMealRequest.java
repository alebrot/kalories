package com.khlebtsov.kalories.dto;

import javax.validation.constraints.NotNull;

public class AddMealRequest {
    @NotNull
    private MealDto meal;

    public MealDto getMeal() {
        return meal;
    }

    public void setMeal(MealDto meal) {
        this.meal = meal;
    }
}
