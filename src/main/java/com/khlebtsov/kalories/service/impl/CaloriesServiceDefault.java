package com.khlebtsov.kalories.service.impl;

import com.khlebtsov.kalories.db.CaloriesPerUserRepository;
import com.khlebtsov.kalories.db.UserRepository;
import com.khlebtsov.kalories.db.entity.CaloriesPerUserEntity;
import com.khlebtsov.kalories.db.entity.UserEntity;
import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.service.CaloriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CaloriesServiceDefault implements CaloriesService {

    private final CaloriesPerUserRepository caloriesPerUserRepository;
    private final UserRepository userRepository;

    @Autowired
    public CaloriesServiceDefault(CaloriesPerUserRepository caloriesPerUserRepository, UserRepository userRepository) {
        this.caloriesPerUserRepository = caloriesPerUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Long getCaloriesForUser(Long userId) throws KaloriesException {
        Optional<Long> caloriesOptional = caloriesPerUserRepository.findByUserId(userId).map(CaloriesPerUserEntity::getNumberOfCalories);
        if (!caloriesOptional.isPresent()) {
            throw new KaloriesException("No threshold set for user ", userId);
        }
        return caloriesOptional.get();
    }


    @Transactional
    @Override
    public void setForUser(Long userId, Long calories) throws KaloriesException {

        Optional<CaloriesPerUserEntity> found = caloriesPerUserRepository.findByUserId(userId);
        if (found.isPresent()) {
            CaloriesPerUserEntity caloriesPerUserEntity = found.get();
            caloriesPerUserEntity.setNumberOfCalories(calories);
            caloriesPerUserRepository.save(caloriesPerUserEntity);
        } else {
            Optional<UserEntity> foundUser = userRepository.findById(userId);

            if (foundUser.isPresent()) {
                CaloriesPerUserEntity caloriesPerUserEntity = new CaloriesPerUserEntity();
                caloriesPerUserEntity.setNumberOfCalories(calories);
                caloriesPerUserEntity.setUser(foundUser.get());
                caloriesPerUserRepository.save(caloriesPerUserEntity);
            } else {
                throw new KaloriesException("User not found for provided id" + userId);
            }

        }
    }
}
