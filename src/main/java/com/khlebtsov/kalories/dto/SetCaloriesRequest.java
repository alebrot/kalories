package com.khlebtsov.kalories.dto;

import javax.validation.constraints.NotNull;

public class SetCaloriesRequest {
    @NotNull
    private Long calories;
    @NotNull
    private Long userId;

    public Long getCalories() {
        return calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
