package com.khlebtsov.kalories;

import com.khlebtsov.kalories.entity.Meal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Repository
public interface MealRepository extends CrudRepository<Meal, Long> {

    @Query(value = "SELECT * from MEAL where MEAL.DATE BETWEEN :from AND :to", nativeQuery = true)
    List<Meal> getMeals(
            @Param("from") @Temporal(TemporalType.DATE) Date from,
            @Param("to") @Temporal(TemporalType.DATE) Date to);

    @Query(value = "SELECT * from MEAL where MEAL.DATE >= :from", nativeQuery = true)
    List<Meal> getMeals(
            @Param("from") @Temporal(TemporalType.DATE) Date from);
}
