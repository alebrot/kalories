package com.khlebtsov.kalories.layers.service.impl;

import com.khlebtsov.kalories.db.entity.CaloriesPerUserEntity;
import com.khlebtsov.kalories.exception.KaloriesException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class CaloriesServiceDefaultTest extends DefaultTestData {

    @Autowired
    protected CaloriesServiceDefault caloriesServiceDefault;

    @Transactional
    @Test
    public void getCaloriesForUser() throws KaloriesException {
        Long caloriesForUser = caloriesServiceDefault.getCaloriesForUser(userEntity.getId());
        Assert.assertNotNull(caloriesForUser);
        Assert.assertEquals(caloriesPerUserEntity.getNumberOfCalories(), caloriesForUser);
    }
    @Transactional
    @Test
    public void setForUser() throws KaloriesException {
        caloriesServiceDefault.setForUser(userEntity.getId(), 1000L);
        CaloriesPerUserEntity caloriesPerUserEntity = caloriesPerUserRepository.findByUserId(userEntity.getId()).get();
        Assert.assertEquals((long) caloriesPerUserEntity.getNumberOfCalories(), 1000L);

    }
}