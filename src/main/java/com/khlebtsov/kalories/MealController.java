package com.khlebtsov.kalories;


import com.khlebtsov.kalories.entity.Meal;
import com.khlebtsov.kalories.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

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

    @RequestMapping(value = "meals", method = RequestMethod.GET)
    public List<Meal> meals(@RequestParam(required = false) String from,
                            @RequestParam(required = false) String to,
                            @RequestParam(required = false) String date) {

        LocalDate fromLocalDate = !StringUtils.isEmpty(from) ? LocalDate.parse(from, DateTimeFormatter.ISO_DATE) : null;
        LocalDate toLocalDate = !StringUtils.isEmpty(to) ? LocalDate.parse(to, DateTimeFormatter.ISO_DATE) : null;

        List<Meal> meals;

        if (date != null) {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            meals = mealService.getMeals(localDate);
        } else {
            if (fromLocalDate != null && toLocalDate != null) {
                if (fromLocalDate.isBefore(toLocalDate)) {
                    throw new IllegalArgumentException("Invalid request from < to");
                }
                meals = mealService.getMeals(fromLocalDate, toLocalDate);
            } else {
                meals = mealService.getMeals();
            }
        }


        return meals;
    }

}
