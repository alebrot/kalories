package com.khlebtsov.kalories.layers.controller.dto.response;

import com.khlebtsov.kalories.layers.facade.impl.CaloriesFacadeDefault;

public class CaloriesCountResponse {
    private CaloriesFacadeDefault.CaloriesStatus caloriesStatus;
    private Long caloriesCount;

    public void setCaloriesStatus(CaloriesFacadeDefault.CaloriesStatus calorisStatus) {
        this.caloriesStatus = calorisStatus;
    }

    public CaloriesFacadeDefault.CaloriesStatus getCaloriesStatus() {
        return caloriesStatus;
    }

    public void setCaloriesCount(Long caloriesCount) {
        this.caloriesCount = caloriesCount;
    }

    public Long getCaloriesCount() {
        return caloriesCount;
    }
}
