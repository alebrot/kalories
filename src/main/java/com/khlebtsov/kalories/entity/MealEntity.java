package com.khlebtsov.kalories.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meal")
public class MealEntity {

    @Id()
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Deprecated
    @Column(name = "date")
    private LocalDateTime timestamp;

    @Column(name = "number_of_calories")
    private Long numberOfCalories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getNumberOfCalories() {
        return numberOfCalories;
    }

    public void setNumberOfCalories(Long numberOfCalories) {
        this.numberOfCalories = numberOfCalories;
    }

    public LocalDateTime getDate() {
        return timestamp;
    }
    @Deprecated
    public void setDate(LocalDateTime date) {
        this.timestamp = date;
    }
}
