package com.khlebtsov.kalories.layers.controller.dto.request;

import javax.validation.constraints.NotNull;

public class AddMealRequest {
    @NotNull
    private MealDto meal;
    @NotNull
    private Long userId;

    public MealDto getMeal() {
        return meal;
    }

    public void setMeal(MealDto meal) {
        this.meal = meal;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
