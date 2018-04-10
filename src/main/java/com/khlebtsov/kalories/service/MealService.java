package com.khlebtsov.kalories.service;

import com.khlebtsov.kalories.MealModel;
import com.khlebtsov.kalories.MealRepository;
import com.khlebtsov.kalories.UserMealRepository;
import com.khlebtsov.kalories.entity.MealEntity;
import com.khlebtsov.kalories.entity.UserMealEntity;
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
import java.util.stream.StreamSupport;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final UserMealRepository userMealRepository;
    private final MealModelEntityMapper mealModelEntityMapper;
    private final Function<UserMealEntity, MealModel> userMealEntityMealModelMapper;

    @Autowired
    public MealService(MealRepository mealRepository, UserMealRepository userMealRepository, MealModelEntityMapper mealModelEntityMapper) {
        this.mealRepository = mealRepository;
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

    public List<MealModel> getMeals(LocalDate from, LocalDate to) {
        return mealRepository.getMeals(Date.valueOf(from), Date.valueOf(to)).stream()
                .map(mealModelEntityMapper::mapBackward)
                .collect(Collectors.toList());
    }

    public List<MealModel> getMeals(LocalDate date) {
        return mealRepository.getMeals(Date.valueOf(date)).stream()
                .map(mealModelEntityMapper::mapBackward)
                .collect(Collectors.toList());
    }

    public List<MealModel> getMeals() {
        return StreamSupport.stream(mealRepository.findAll().spliterator(), false)
                .map(mealModelEntityMapper::mapBackward)
                .collect(Collectors.toList());
    }

    public MealModel addMeal(MealModel mealModel) {
        MealEntity mealEntity = mealModelEntityMapper.map(mealModel);
        MealEntity save = mealRepository.save(mealEntity);
        return mealModelEntityMapper.mapBackward(save);
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
