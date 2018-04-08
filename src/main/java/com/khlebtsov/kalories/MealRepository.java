package com.khlebtsov.kalories;

import com.khlebtsov.kalories.entity.Meal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Collection;
import java.util.Date;

@Repository
public interface MealRepository extends CrudRepository<Meal, Long> {

    @Query(value = "SELECT * from DEMO.MEAL where MEAL.DATE BETWEEN :from AND :to", nativeQuery = true)
    Collection<Meal> getMeals(
            @Param("from") @Temporal(TemporalType.DATE) Date from,
            @Param("to") @Temporal(TemporalType.DATE) Date to);
}
