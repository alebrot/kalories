package com.khlebtsov.kalories.layers.service.impl;

import com.khlebtsov.kalories.db.entity.UserMealEntity;
import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.model.MealModel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;


public class MealServiceDefaultTest extends DefaultTestData {

    @Autowired
    private MealServiceDefault mealServiceDefault;

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