package com.khlebtsov.kalories.service.impl;

import com.khlebtsov.kalories.db.entity.CaloriesPerUserEntity;
import com.khlebtsov.kalories.exception.KaloriesException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CaloriesServiceDefaultTest extends DefaultTestData {

    @Autowired
    protected CaloriesServiceDefault caloriesServiceDefault;


    @Test
    public void getCaloriesForUser() {
        Optional<Long> caloriesForUser = caloriesServiceDefault.getCaloriesForUser(userEntity.getId());
        Assert.assertTrue(caloriesForUser.isPresent());
        Assert.assertEquals(caloriesPerUserEntity.getNumberOfCalories(), caloriesForUser.get());
    }

    @Test
    public void setForUser() throws KaloriesException {
        caloriesServiceDefault.setForUser(userEntity.getId(), 1000L);
        CaloriesPerUserEntity caloriesPerUserEntity = caloriesPerUserRepository.findByUserId(userEntity.getId()).get();
        Assert.assertEquals((long) caloriesPerUserEntity.getNumberOfCalories(), 1000L);

    }
}