package com.khlebtsov.kalories.service;

import com.khlebtsov.kalories.MealModel;
import com.khlebtsov.kalories.MealRepository;
import com.khlebtsov.kalories.UserMealRepository;
import com.khlebtsov.kalories.UserRepository;
import com.khlebtsov.kalories.entity.MealEntity;
import com.khlebtsov.kalories.entity.UserEntity;
import com.khlebtsov.kalories.entity.UserMealEntity;
import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.mapper.MealModelEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final UserMealRepository userMealRepository;
    private final MealModelEntityMapper mealModelEntityMapper;
    private final Function<UserMealEntity, MealModel> userMealEntityMealModelMapper;

    @Autowired
    public MealService(MealRepository mealRepository, UserRepository userRepository, UserMealRepository userMealRepository, MealModelEntityMapper mealModelEntityMapper) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
        this.userMealRepository = userMealRepository;
        this.mealModelEntityMapper = mealModelEntityMapper;
        this.userMealEntityMealModelMapper = userMealEntity -> {
            MealEntity meal = userMealEntity.getMeal();
            MealModel mealModel = mealModelEntityMapper.mapBackward(meal);
            LocalDateTime updatedAt = userMealEntity.getUpdatedAt();
            mealModel.setTimestamp(updatedAt);
            return mealModel;
        };
    }

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
            UserMealEntity userMealEntity = new UserMealEntity();
            if (timestamp != null) {
                userMealEntity.setUpdatedAt(timestamp);
            }
            userMealEntity.setUser(userOptional.get());

            MealEntity mealEntity = new MealEntity();
            mealEntity.setNumberOfCalories(numberOfCalories);
            mealEntity.setText(text);
            mealEntity.setDate(timestamp);
            MealEntity saveMealEntity = mealRepository.save(mealEntity);

            userMealEntity.setMeal(saveMealEntity);
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

        MealEntity meal = userMealEntitySaved.getMeal();
        MealModel mealModelAfterSaving = mealModelEntityMapper.mapBackward(meal);
        mealModelAfterSaving.setId(userMealEntitySaved.getId());

        return mealModelAfterSaving;
    }

    public void deleteMeal(long id) {
        mealRepository.deleteById(id);
    }

    public void deleteMeal(MealModel mealModel) {
        MealEntity mealEntity = mealModelEntityMapper.map(mealModel);
        mealRepository.delete(mealEntity);
    }


    public Optional<MealModel> getMeal(long id) {
        return mealRepository.findById(id).map(mealModelEntityMapper::mapBackward);
    }


    @Transactional
    public List<MealModel> getMealsByUser(Long userId) {
        List<UserMealEntity> userMealEntities = userMealRepository.findByUserId(userId);
        return userMealEntities.stream()
                .map(userMealEntityMealModelMapper)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MealModel> getMealsByUser(Long userId, LocalDate from, LocalDate to) {
        return userMealRepository.getMeals(userId, Date.valueOf(from), Date.valueOf(to)).stream()
                .map(userMealEntityMealModelMapper)
                .collect(Collectors.toList());

    }

    @Transactional
    public List<MealModel> getMealsByUser(Long userId, LocalDate date) {
        return userMealRepository.getMeals(userId, Date.valueOf(date)).stream()
                .map(userMealEntityMealModelMapper)
                .collect(Collectors.toList());

    }

}
