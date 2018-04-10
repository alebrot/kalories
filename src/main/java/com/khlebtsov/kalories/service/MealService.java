package com.khlebtsov.kalories.service;

import com.khlebtsov.kalories.model.MealModel;
import com.khlebtsov.kalories.db.MealRepository;
import com.khlebtsov.kalories.db.UserMealRepository;
import com.khlebtsov.kalories.db.UserRepository;
import com.khlebtsov.kalories.db.entity.MealEntity;
import com.khlebtsov.kalories.db.entity.UserEntity;
import com.khlebtsov.kalories.db.entity.UserMealEntity;
import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.mapper.MealModelUserMealEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final UserMealRepository userMealRepository;
    private final MealModelUserMealEntityMapper mealModelUserMealEntityMapper;

    @Autowired
    public MealService(MealRepository mealRepository, UserRepository userRepository, UserMealRepository userMealRepository, MealModelUserMealEntityMapper mealModelUserMealEntityMapper) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
        this.userMealRepository = userMealRepository;
        this.mealModelUserMealEntityMapper = mealModelUserMealEntityMapper;
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

//    public void deleteMeal(long id) {
//        mealRepository.deleteById(id);
//    }
//
//    public void deleteMeal(MealModel mealModel) {
//        MealEntity mealEntity = mealModelUserMealEntityMapper.map(mealModel);
//        mealRepository.delete(mealEntity);
//    }


    @Transactional
    public List<MealModel> getMealsByUser(Long userId) {
        List<UserMealEntity> userMealEntities = userMealRepository.findByUserId(userId);
        return userMealEntities.stream()
                .map(mealModelUserMealEntityMapper::mapBackward)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MealModel> getMealsByUser(Long userId, LocalDate from, LocalDate to) {
        return userMealRepository.getMeals(userId, Date.valueOf(from), Date.valueOf(to)).stream()
                .map(mealModelUserMealEntityMapper::mapBackward)
                .collect(Collectors.toList());

    }

    @Transactional
    public List<MealModel> getMealsByUser(Long userId, LocalDate date) {
        return userMealRepository.getMeals(userId, Date.valueOf(date)).stream()
                .map(mealModelUserMealEntityMapper::mapBackward)
                .collect(Collectors.toList());

    }

}
