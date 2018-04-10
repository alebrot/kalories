package com.khlebtsov.kalories.service.impl;

import com.khlebtsov.kalories.db.MealRepository;
import com.khlebtsov.kalories.db.UserMealRepository;
import com.khlebtsov.kalories.db.UserRepository;
import com.khlebtsov.kalories.db.entity.MealEntity;
import com.khlebtsov.kalories.db.entity.UserEntity;
import com.khlebtsov.kalories.db.entity.UserMealEntity;
import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.model.MealModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MealServiceDefaultTest {

    @Autowired
    private MealServiceDefault mealServiceDefault;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMealRepository userMealRepository;
    private MealEntity mealEntity1;
    private MealEntity mealEntity2;
    private MealEntity mealEntity3;
    private UserEntity userEntity;
    private UserMealEntity userMealEntity1;


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
    }

    @Transactional
    @Test
    public void createOrUpdateMealForUser() throws KaloriesException {

        //update
        long doubledNumberOfCalories = mealEntity1.getNumberOfCalories() * 2;
        MealModel mealModelToUpdate = new MealModel(userMealEntity1.getId(), mealEntity1.getText(), userMealEntity1.getUpdatedAt(), doubledNumberOfCalories);
        MealModel mealModelUpdated = mealServiceDefault.createOrUpdateMealForUser(userEntity.getId(), mealModelToUpdate);
        Assert.assertEquals(mealModelToUpdate.getNumberOfCalories(), mealModelUpdated.getNumberOfCalories());

        //create

        MealModel mealModelToCreate = new MealModel(null, "new meal", LocalDateTime.now(), 10L);
        MealModel mealModelCreated = mealServiceDefault.createOrUpdateMealForUser(userEntity.getId(), mealModelToCreate);
        Assert.assertEquals(mealModelToCreate.getText(), mealModelCreated.getText());
    }

    @Test
    public void deleteMealById() throws KaloriesException {
        mealServiceDefault.deleteMealById(userMealEntity1.getId());
        Optional<UserMealEntity> found = userMealRepository.findById(userMealEntity1.getId());
        Assert.assertFalse(found.isPresent());
    }

    @Test
    @Transactional
    public void getMealsByUser() {
        List<MealModel> mealsByUser = mealServiceDefault.getMealsByUser(userEntity.getId());
        assertEquals(3, mealsByUser.size());
        Assert.assertEquals(mealEntity1.getText(), mealsByUser.get(0).getText());
        Assert.assertEquals(mealEntity2.getText(), mealsByUser.get(1).getText());
        Assert.assertEquals(mealEntity3.getText(), mealsByUser.get(2).getText());
    }

    @Test
    @Transactional
    public void getMealsByUserAndDate() {
        List<MealModel> mealsByUser = mealServiceDefault.getMealsByUser(userEntity.getId(), LocalDate.now());
        assertEquals(1, mealsByUser.size());
        Assert.assertEquals(mealEntity3.getText(), mealsByUser.get(0).getText());
    }

    @Transactional
    @Test
    public void getMealsByUserAndFromToDate() {
        List<MealModel> mealsByUser = mealServiceDefault.getMealsByUser(userEntity.getId(), LocalDate.now().minusDays(1), LocalDate.now());
        assertEquals(2, mealsByUser.size());
        Assert.assertEquals(mealEntity2.getText(), mealsByUser.get(0).getText());
        Assert.assertEquals(mealEntity3.getText(), mealsByUser.get(1).getText());
    }
}