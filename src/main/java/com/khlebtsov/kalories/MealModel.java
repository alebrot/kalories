package com.khlebtsov.kalories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealModel {
    private Long id;

    private String text;

    private LocalDate date;

    private LocalTime time;

    private int numberOfCalories;

    public MealModel(Long id, String text, LocalDateTime timestamp, LocalTime time, int numberOfCalories) {
        this.id = id;
        this.text = text;
        this.date = timestamp.toLocalDate();
        this.time = timestamp.toLocalTime();
        this.numberOfCalories = numberOfCalories;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getNumberOfCalories() {
        return numberOfCalories;
    }
}
