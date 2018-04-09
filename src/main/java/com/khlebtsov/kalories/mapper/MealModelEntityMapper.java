package com.khlebtsov.kalories.mapper;

import com.khlebtsov.kalories.MealModel;
import com.khlebtsov.kalories.entity.MealEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class MealModelEntityMapper implements BiMapper<MealModel, MealEntity> {

    @Override
    public MealEntity map(@NonNull MealModel mealModel) {
        MealEntity mealEntity = new MealEntity();
        mealEntity.setId(mealModel.getId());
        mealEntity.setNumberOfCalories(mealModel.getNumberOfCalories());
        mealEntity.setDate(mealModel.getTimestamp());
        mealEntity.setText(mealModel.getText());
        return mealEntity;
    }

    /**
     * @param source object
     * @return target object
     */
    @Override
    public MealModel mapBackward(MealEntity source) {
        return new MealModel(source.getId(), source.getText(), source.getDate(), source.getNumberOfCalories());
    }
}
