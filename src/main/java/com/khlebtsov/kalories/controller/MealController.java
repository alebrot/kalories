package com.khlebtsov.kalories.controller;

import com.khlebtsov.kalories.controller.dto.request.AddMealRequest;
import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.model.MealModel;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface MealController {
    @RequestMapping(value = "meals", method = RequestMethod.GET)
    List<MealModel> meals(@RequestParam Long userId,
                          @RequestParam(required = false) String from,
                          @RequestParam(required = false) String to,
                          @RequestParam(required = false) String date);

    @RequestMapping(value = "meals", method = RequestMethod.POST)
    MealModel addMeals(@RequestBody AddMealRequest request) throws KaloriesException;

    @RequestMapping(value = "meals/{id}", method = RequestMethod.PATCH)
    MealModel updateMeal(@RequestBody AddMealRequest request, @PathVariable(name = "id") long id) throws KaloriesException;

    @Transactional
    @RequestMapping(value = "meals/{id}", method = RequestMethod.DELETE)
    void deleteMeal(@PathVariable(name = "id") long id) throws KaloriesException;
}
