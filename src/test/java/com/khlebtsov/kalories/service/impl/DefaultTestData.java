package com.khlebtsov.kalories.service.impl;

import com.khlebtsov.kalories.db.CaloriesPerUserRepository;
import com.khlebtsov.kalories.db.MealRepository;
import com.khlebtsov.kalories.db.UserMealRepository;
import com.khlebtsov.kalories.db.UserRepository;
import com.khlebtsov.kalories.db.entity.CaloriesPerUserEntity;
import com.khlebtsov.kalories.db.entity.MealEntity;
import com.khlebtsov.kalories.db.entity.UserEntity;
import com.khlebtsov.kalories.db.entity.UserMealEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultTestData {

    @Autowired
    protected MealRepository mealRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CaloriesPerUserRepository caloriesPerUserRepository;

    @Autowired
    protected UserMealRepository userMealRepository;
    protected MealEntity mealEntity1;
    protected MealEntity mealEntity2;
    protected MealEntity mealEntity3;
    protected UserEntity userEntity;
    protected UserMealEntity userMealEntity1;
    protected CaloriesPerUserEntity caloriesPerUserEntity;


    @Before
    public void setup() {

        //set up 3 meals
        mealEntity1 = new MealEntity();
        mealEntity1.setNumberOfCalories(100L);
        mealEntity1.setText("Meal 1");
        mealEntity1 = mealRepository.save(mealEntity1);


        mealEntity2 = new MealEntity();
        mealEntity2.setNumberOfCalories(200L);
        mealEntity2.setText("Meal 2");
        mealEntity2 = mealRepository.save(mealEntity2);


        mealEntity3 = new MealEntity();
        mealEntity3.setNumberOfCalories(300L);
        mealEntity3.setText("Meal 3");
        mealEntity3 = mealRepository.save(mealEntity3);


        //set up user
        userEntity = new UserEntity();
        userEntity.setFirstName("User FirstName");
        userEntity.setLastName("User LastName");
        userEntity = userRepository.save(userEntity);


        //set up user meals
        userMealEntity1 = new UserMealEntity();
        userMealEntity1.setUpdatedAt(LocalDateTime.now().minusDays(2));
        userMealEntity1.setMeal(mealEntity1);
        userMealEntity1.setUser(userEntity);
        userMealRepository.save(userMealEntity1);

        UserMealEntity userMealEntity2 = new UserMealEntity();
        userMealEntity2.setUpdatedAt(LocalDateTime.now().minusDays(1));
        userMealEntity2.setMeal(mealEntity2);
        userMealEntity2.setUser(userEntity);
        userMealRepository.save(userMealEntity2);

        UserMealEntity userMealEntity3 = new UserMealEntity();
        userMealEntity3.setUpdatedAt(LocalDateTime.now());
        userMealEntity3.setMeal(mealEntity3);
        userMealEntity3.setUser(userEntity);
        userMealRepository.save(userMealEntity3);


        caloriesPerUserEntity = new CaloriesPerUserEntity();
        caloriesPerUserEntity.setNumberOfCalories(500L);
        caloriesPerUserEntity.setUser(userEntity);
        caloriesPerUserRepository.save(caloriesPerUserEntity);

    }


    @Test
    public void defaultTest() {

    }
}