package com.khlebtsov.kalories;

import com.khlebtsov.kalories.entity.MealEntity;
import com.khlebtsov.kalories.entity.UserMealEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Repository
public interface UserMealRepository extends CrudRepository<UserMealEntity, Long> {
    List<UserMealEntity> findByUserId(Long id);

    @Query(value = "select USER_MEALS.*  from USER_MEALS inner join MEAL on MEAL.ID = USER_MEALS.MEAL_ID  inner join USER on USER.ID =  USER_MEALS.USER_ID where (cast(USER_MEALS.UPDATED_AT as DATE) between :from and :to) and USER.ID = :user_id ", nativeQuery = true)
    List<UserMealEntity> getMeals(
            @Param("user_id") long userId,
            @Param("from") @Temporal(TemporalType.DATE) Date from,
            @Param("to") @Temporal(TemporalType.DATE) Date to);

    @Query(value = "select USER_MEALS.*  from USER_MEALS inner join MEAL on MEAL.ID = USER_MEALS.MEAL_ID  inner join USER on USER.ID =  USER_MEALS.USER_ID where (cast(USER_MEALS.UPDATED_AT as DATE) = :date) and USER.ID = :user_id ", nativeQuery = true)
    List<UserMealEntity> getMeals(
            @Param("user_id") long userId,
            @Param("date") @Temporal(TemporalType.DATE) Date date);
}
