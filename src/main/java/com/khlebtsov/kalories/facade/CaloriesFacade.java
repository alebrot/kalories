package com.khlebtsov.kalories.facade;

import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.facade.impl.CaloriesFacadeDefault;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.util.Optional;

public interface CaloriesFacade {
    Optional<Pair<Long, CaloriesFacadeDefault.CaloriesStatus>> getCaloriesByUser(Long userId, LocalDate from, LocalDate to) throws KaloriesException;

    Optional<Pair<Long, CaloriesFacadeDefault.CaloriesStatus>> getCaloriesByUser(Long userId, LocalDate date) throws KaloriesException;

    Optional<Pair<Long, CaloriesFacadeDefault.CaloriesStatus>> getCaloriesByUser(Long userId) throws KaloriesException;

    Long getCaloriesThresholdForUser(Long userId) throws KaloriesException;
}
