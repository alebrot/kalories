package com.khlebtsov.kalories.service.impl;

import com.khlebtsov.kalories.db.MealRepository;
import com.khlebtsov.kalories.db.UserMealRepository;
import com.khlebtsov.kalories.db.UserRepository;
import com.khlebtsov.kalories.db.entity.MealEntity;
import com.khlebtsov.kalories.db.entity.UserEntity;
import com.khlebtsov.kalories.db.entity.UserMealEntity;
import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.mapper.MealModelUserMealEntityMapper;
import com.khlebtsov.kalories.model.MealModel;
import com.khlebtsov.kalories.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MealServiceDefault implements MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final UserMealRepository userMealRepository;
    private final MealModelUserMealEntityMapper mealModelUserMealEntityMapper;

    @Autowired
    public MealServiceDefault(MealRepository mealRepository, UserRepository userRepository, UserMealRepository userMealRepository, MealModelUserMealEntityMapper mealModelUserMealEntityMapper) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
        this.userMealRepository = userMealRepository;
        this.mealModelUserMealEntityMapper = mealModelUserMealEntityMapper;
    }

    @Override
    @Transactional
    public MealModel createOrUpdateMealForUser(Long userId, MealModel mealModel) throws KaloriesException {

        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new KaloriesException("User not found for id " + userId);
        }

        Long id = mealModel.getId();
        String text = mealModel.getText();
        Long numberOfCalories = mealModel.getNumberOfCalories();
        LocalDateTime timestamp = mealModel.getTimestamp();

        UserMealEntity userMealEntitySaved;
        if (id == null) {  //create

            UserMealEntity userMealEntity = mealModelUserMealEntityMapper.map(mealModel);
            userMealEntity.setUser(userOptional.get());
            mealRepository.save(userMealEntity.getMeal());
            userMealEntitySaved = userMealRepository.save(userMealEntity);

        } else {//update
            Optional<UserMealEntity> foundUserMealEntity = userMealRepository.findById(id);
            if (foundUserMealEntity.isPresent()) {
                UserMealEntity userMealEntity = foundUserMealEntity.get();
                if (timestamp != null) {
                    userMealEntity.setUpdatedAt(timestamp);
                }
                MealEntity foundMeal = userMealEntity.getMeal();
                foundMeal.setText(text);
                foundMeal.setNumberOfCalories(numberOfCalories);
                userMealEntitySaved = userMealRepository.save(userMealEntity);
            } else {
                throw new KaloriesException("Unable to update Meal. Meal not found for id " + id);
            }
        }

        return mealModelUserMealEntityMapper.mapBackward(userMealEntitySaved);
    }

    @Override
    @Transactional
    public void deleteMealById(Long id) throws KaloriesException {
        Optional<UserMealEntity> foundUserMeal = userMealRepository.findById(id);
        if (!foundUserMeal.isPresent()) {
            throw new KaloriesException("Not Found Meal for id " + id);
        }
        UserMealEntity userMealEntity = foundUserMeal.get();
        userMealRepository.delete(userMealEntity);
    }

    @Override
    @Transactional
    public List<MealModel> getMealsByUser(Long userId) {
        List<UserMealEntity> userMealEntities = userMealRepository.findByUserId(userId);
        return userMealEntities.stream()
                .map(mealModelUserMealEntityMapper::mapBackward)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<MealModel> getMealsByUser(Long userId, LocalDate from, LocalDate to) {
        return userMealRepository.getMeals(userId, from.format(DateTimeFormatter.ISO_DATE), to.format(DateTimeFormatter.ISO_DATE)).stream()
                .map(mealModelUserMealEntityMapper::mapBackward)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public List<MealModel> getMealsByUser(Long userId, LocalDate date) {
        return userMealRepository.getMeals(userId, date.format(DateTimeFormatter.ISO_DATE)).stream()
                .map(mealModelUserMealEntityMapper::mapBackward)
                .collect(Collectors.toList());

    }

}
