package com.khlebtsov.kalories.entity;

import javax.persistence.*;

@Entity
@Table(name = "calories_per_user")
public class CaloriesPerUserEntity {

    @Id()
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_calories")
    private Long numberOfCalories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;


    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Long getNumberOfCalories() {
        return numberOfCalories;
    }

    public void setNumberOfCalories(Long numberOfCalories) {
        this.numberOfCalories = numberOfCalories;
    }
}
