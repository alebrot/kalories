package com.khlebtsov.kalories.service;

import com.khlebtsov.kalories.exception.KaloriesException;

import java.util.Optional;

public interface CaloriesService {
    Optional<Long> getCaloriesForUser(Long userId);

    void setForUser(Long userId, Long calories) throws KaloriesException;
}
