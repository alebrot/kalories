package com.khlebtsov.kalories;


import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealModel {
    @Nullable
    private Long id;
    private String text;
    private LocalDate date;
    @Nullable
    private LocalTime time;

    private int numberOfCalories;


    private MealModel() {
    }

    public MealModel(Long id, String text, LocalDateTime timestamp, int numberOfCalories) {
        this.id = id;
        this.text = text;
        this.date = timestamp.toLocalDate();
        this.time = timestamp.toLocalTime();
        this.numberOfCalories = numberOfCalories;
    }

    public MealModel(String text, int numberOfCalories) {
        this(null, text, LocalDateTime.now(), numberOfCalories);
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

    @Nullable
    public LocalDateTime getTimestamp() {
        LocalDateTime localDateTime = null;
        if (date != null && time != null) {
            localDateTime = LocalDateTime.of(date, time);
        }
        return localDateTime;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.date = timestamp.toLocalDate();
        this.time = timestamp.toLocalTime();
    }
}
