package com.khlebtsov.kalories.db;

import com.khlebtsov.kalories.db.entity.MealEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MealRepository extends CrudRepository<MealEntity, Long> {
    Optional<MealEntity> findByTextAndNumberOfCalories(String text, Long umberOfCalories);
}
