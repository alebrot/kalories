package com.khlebtsov.kalories.service;

import com.khlebtsov.kalories.CaloriesPerUserRepository;
import com.khlebtsov.kalories.UserRepository;
import com.khlebtsov.kalories.entity.CaloriesPerUserEntity;
import com.khlebtsov.kalories.entity.UserEntity;
import com.khlebtsov.kalories.exception.KaloriesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CaloriesService {

    private final CaloriesPerUserRepository caloriesPerUserRepository;
    private final UserRepository userRepository;

    @Autowired
    public CaloriesService(CaloriesPerUserRepository caloriesPerUserRepository, UserRepository userRepository) {
        this.caloriesPerUserRepository = caloriesPerUserRepository;
        this.userRepository = userRepository;
    }

    public Optional<Long> getCaloriesForUser(Long userId) {
        return caloriesPerUserRepository.findByUserId(userId).map(CaloriesPerUserEntity::getNumberOfCalories);
    }

    @Transactional
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
