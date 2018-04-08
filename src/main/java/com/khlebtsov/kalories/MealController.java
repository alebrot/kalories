package com.khlebtsov.kalories;


import com.khlebtsov.kalories.entity.Meal;
import com.khlebtsov.kalories.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;

@RestController
public class MealController {

    private final MealService mealService;

    @Autowired
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @RequestMapping(value = "go", method = RequestMethod.GET)
    public Collection<Meal> go() {
//        mealService.addMeal(new Meal());
        Collection<Meal> meals = mealService.getMeals(LocalDate.now().minusDays(1), LocalDate.now());
        return meals;
    }

}
