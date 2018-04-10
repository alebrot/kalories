package com.khlebtsov.kalories;

import com.khlebtsov.kalories.entity.MealEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Repository
public interface MealRepository extends CrudRepository<MealEntity, Long> {

    @Query(value = "SELECT * from MEAL where cast(MEAL.DATE as DATE) BETWEEN :from AND :to", nativeQuery = true)
    List<MealEntity> getMeals(
            @Param("from") @Temporal(TemporalType.DATE) Date from,
            @Param("to") @Temporal(TemporalType.DATE) Date to);

    @Query(value = "SELECT * from MEAL where cast(MEAL.DATE as DATE) = :date", nativeQuery = true)
    List<MealEntity> getMeals(
            @Param("date") @Temporal(TemporalType.DATE) Date date);


    @Query(value = "select MEAL.* from USER_MEALS inner join MEAL on MEAL.ID=USER_MEALS.MEAL_ID where cast(MEAL.DATE as DATE) = :date and USER_MEALS.USER_ID = :user_id", nativeQuery = true)
    List<MealEntity> getMealsForUser(
            @Param("user_id") long userId,
            @Param("date") @Temporal(TemporalType.DATE) Date date);


    @Query(value = "select MEAL.* from USER_MEALS inner join MEAL on MEAL.ID=USER_MEALS.MEAL_ID where (cast(MEAL.DATE as DATE) between :from and :to) and USER_MEALS.USER_ID = :user_id", nativeQuery = true)
    List<MealEntity> getMeals(
            @Param("user_id") long userId,
            @Param("from") @Temporal(TemporalType.DATE) Date from,
            @Param("to") @Temporal(TemporalType.DATE) Date to);
}
