package com.khlebtsov.kalories;

import com.khlebtsov.kalories.entity.MealEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MealRepository extends CrudRepository<MealEntity, Long> {
    Optional<MealEntity> findByTextAndNumberOfCalories(String text, Long umberOfCalories);
}
