package com.khlebtsov.kalories.mapper;

import com.khlebtsov.kalories.model.MealModel;
import com.khlebtsov.kalories.db.entity.MealEntity;
import com.khlebtsov.kalories.db.entity.UserMealEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class MealModelUserMealEntityMapper implements BiMapper<MealModel, UserMealEntity> {

    @Override
    public UserMealEntity map(@NonNull MealModel mealModel) {

        UserMealEntity userMealEntity = new UserMealEntity();
        userMealEntity.setId(mealModel.getId());

        MealEntity mealEntity = new MealEntity();
        mealEntity.setNumberOfCalories(mealModel.getNumberOfCalories());
        mealEntity.setText(mealModel.getText());
        userMealEntity.setMeal(mealEntity);

        if (mealModel.getTimestamp() != null) {
            userMealEntity.setUpdatedAt(mealModel.getTimestamp());
        }
        return userMealEntity;
    }

    /**
     * @param source object
     * @return target object
     */
    @Override
    public MealModel mapBackward(UserMealEntity source) {
        MealEntity meal = source.getMeal();
        return new MealModel(source.getId(), meal.getText(), source.getUpdatedAt(), meal.getNumberOfCalories());
    }
}
