package com.khlebtsov.kalories.service;

import com.khlebtsov.kalories.MealModel;
import com.khlebtsov.kalories.MealRepository;
import com.khlebtsov.kalories.entity.MealEntity;
import com.khlebtsov.kalories.mapper.MealModelEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MealService {

    private final MealRepository mealRepository;

    private final MealModelEntityMapper mealModelEntityMapper;

    @Autowired
    public MealService(MealRepository mealRepository, MealModelEntityMapper mealModelEntityMapper) {
        this.mealRepository = mealRepository;
        this.mealModelEntityMapper = mealModelEntityMapper;
    }

    public List<MealModel> getMeals(LocalDateTime from, LocalDateTime to) {
        return mealRepository.getMeals(Timestamp.valueOf(from), Timestamp.valueOf(to)).stream()
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
}
