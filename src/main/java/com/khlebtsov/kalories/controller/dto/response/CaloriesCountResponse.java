package com.khlebtsov.kalories.controller.dto.response;

import com.khlebtsov.kalories.facade.CaloriesFacade;

public class CaloriesCountResponse {
    private CaloriesFacade.CaloriesStatus caloriesStatus;
    private Long caloriesCount;

    public void setCaloriesStatus(CaloriesFacade.CaloriesStatus calorisStatus) {
        this.caloriesStatus = calorisStatus;
    }

    public CaloriesFacade.CaloriesStatus getCaloriesStatus() {
        return caloriesStatus;
    }

    public void setCaloriesCount(Long caloriesCount) {
        this.caloriesCount = caloriesCount;
    }

    public Long getCaloriesCount() {
        return caloriesCount;
    }
}
