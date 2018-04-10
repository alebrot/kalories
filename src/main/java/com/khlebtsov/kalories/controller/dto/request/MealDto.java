package com.khlebtsov.kalories.controller.dto.request;

public class MealDto {

    private String text;

    private long numberOfCalories;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getNumberOfCalories() {
        return numberOfCalories;
    }

    public void setNumberOfCalories(int numberOfCalories) {
        this.numberOfCalories = numberOfCalories;
    }
}
