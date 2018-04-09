package com.khlebtsov.kalories;


import com.khlebtsov.kalories.dto.AddMealRequest;
import com.khlebtsov.kalories.entity.Meal;
import com.khlebtsov.kalories.mapper.DtoModelMapper;
import com.khlebtsov.kalories.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class MealController {

    private final MealService mealService;
    private final DtoModelMapper dtoModelMapper;

    @Autowired
    public MealController(MealService mealService, DtoModelMapper dtoModelMapper) {
        this.mealService = mealService;
        this.dtoModelMapper = dtoModelMapper;
    }

    @RequestMapping(value = "go", method = RequestMethod.GET)
    public Collection<Meal> go() {
//        mealService.addMeal(new Meal());
        Collection<Meal> meals = mealService.getMeals(LocalDateTime.now().minusDays(1), LocalDateTime.now());
        return meals;
    }

    @RequestMapping(value = "meals", method = RequestMethod.GET)
    public List<Meal> meals(@RequestParam(required = false) String from,
                            @RequestParam(required = false) String to,
                            @RequestParam(required = false) String date) {

        LocalDateTime fromLocalDate = !StringUtils.isEmpty(from) ? LocalDateTime.parse(from, DateTimeFormatter.ISO_DATE_TIME) : null;
        LocalDateTime toLocalDate = !StringUtils.isEmpty(to) ? LocalDateTime.parse(to, DateTimeFormatter.ISO_DATE_TIME) : null;

        List<Meal> meals;

        if (date != null) {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            meals = mealService.getMeals(localDate);
        } else {
            if (fromLocalDate != null && toLocalDate != null) {
                if (toLocalDate.isBefore(fromLocalDate)) {
                    throw new IllegalArgumentException("Invalid request from < to");
                }
                meals = mealService.getMeals(fromLocalDate, toLocalDate);
            } else {
                meals = mealService.getMeals();
            }
        }


        return meals;
    }

    @RequestMapping(value = "meals", method = RequestMethod.POST)
    public Meal meals(@RequestBody AddMealRequest request) {
        Meal mealToAdd = dtoModelMapper.apply(request.getMeal());
        return mealService.addMeal(mealToAdd);
    }

    @Transactional
    @RequestMapping(value = "meals/{id}", method = RequestMethod.DELETE)
    public Meal meals(@PathVariable(name = "id") long id) {
        Optional<Meal> meal = mealService.getMeal(id);
        if (!meal.isPresent()) {
            throw new IllegalArgumentException("Meal does'n exist");
        }
        Meal mealFound = meal.get();
        mealService.deleteMeal(mealFound);
        return mealFound;
    }

}
