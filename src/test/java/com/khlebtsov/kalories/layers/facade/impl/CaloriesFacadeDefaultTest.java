package com.khlebtsov.kalories.layers.facade.impl;

import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.layers.service.impl.DefaultTestData;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.util.Optional;

public class CaloriesFacadeDefaultTest extends DefaultTestData {

    @Autowired
    private CaloriesFacadeDefault caloriesFacadeDefault;

    @Test
    public void getCaloriesByUserByDateRange() throws KaloriesException {
        Optional<Pair<Long, CaloriesFacadeDefault.CaloriesStatus>> caloriesByUser = caloriesFacadeDefault.getCaloriesByUser(userEntity.getId(), LocalDate.now().minusDays(1), LocalDate.now());
        Assert.assertTrue(caloriesByUser.isPresent());
        Pair<Long, CaloriesFacadeDefault.CaloriesStatus> pair = caloriesByUser.get();
        Assert.assertEquals(CaloriesFacadeDefault.CaloriesStatus.OK, pair.getSecond());
        Assert.assertEquals(500L, (long) pair.getFirst());
    }

    @Test
    public void getCaloriesByUserByDate() throws KaloriesException {
        Optional<Pair<Long, CaloriesFacadeDefault.CaloriesStatus>> caloriesByUser = caloriesFacadeDefault.getCaloriesByUser(userEntity.getId(), LocalDate.now());
        Assert.assertTrue(caloriesByUser.isPresent());
        Pair<Long, CaloriesFacadeDefault.CaloriesStatus> pair = caloriesByUser.get();
        Assert.assertEquals(CaloriesFacadeDefault.CaloriesStatus.OK, pair.getSecond());
        Assert.assertEquals(300L, (long) pair.getFirst());
    }

    @Test
    public void getCaloriesByUser() throws KaloriesException {
        Optional<Pair<Long, CaloriesFacadeDefault.CaloriesStatus>> caloriesByUser = caloriesFacadeDefault.getCaloriesByUser(userEntity.getId());
        Assert.assertTrue(caloriesByUser.isPresent());
        Pair<Long, CaloriesFacadeDefault.CaloriesStatus> pair = caloriesByUser.get();
        Assert.assertEquals(CaloriesFacadeDefault.CaloriesStatus.WARNING_EXCEED_THRESHOLD, pair.getSecond());
        Assert.assertEquals(600L, (long) pair.getFirst());

    }

    @Test
    public void getCaloriesThresholdForUser() throws KaloriesException {
        Long caloriesThresholdForUser = caloriesFacadeDefault.getCaloriesThresholdForUser(userEntity.getId());
        Assert.assertEquals(500L, (long) caloriesThresholdForUser);
    }
}