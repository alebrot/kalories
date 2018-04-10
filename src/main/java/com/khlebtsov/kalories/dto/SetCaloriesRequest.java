package com.khlebtsov.kalories.dto;

public class SetCaloriesRequest {
    private Long calories;
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
