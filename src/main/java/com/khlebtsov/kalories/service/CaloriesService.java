package com.khlebtsov.kalories.service;

import com.khlebtsov.kalories.exception.KaloriesException;

public interface CaloriesService {
    Long getCaloriesForUser(Long userId) throws KaloriesException;

    void setForUser(Long userId, Long calories) throws KaloriesException;
}
