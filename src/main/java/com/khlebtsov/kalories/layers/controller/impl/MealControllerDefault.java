package com.khlebtsov.kalories.layers.controller.impl;


import com.khlebtsov.kalories.layers.controller.MealController;
import com.khlebtsov.kalories.layers.controller.dto.request.AddMealRequest;
import com.khlebtsov.kalories.model.MealModel;
import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.mapper.MealDtoModelMapper;
import com.khlebtsov.kalories.layers.service.impl.MealServiceDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class MealControllerDefault implements MealController {

    private final MealServiceDefault mealService;
    private final MealDtoModelMapper mealDtoModelMapper;

    @Autowired
    public MealControllerDefault(MealServiceDefault mealService, MealDtoModelMapper mealDtoModelMapper) {
        this.mealService = mealService;
        this.mealDtoModelMapper = mealDtoModelMapper;
    }


    @Override
    @RequestMapping(value = "meals", method = RequestMethod.GET)
    public List<MealModel> meals(@RequestParam Long userId,
                                 @RequestParam(required = false) String from,
                                 @RequestParam(required = false) String to,
                                 @RequestParam(required = false) String date) {


        LocalDate fromLocalDate = !StringUtils.isEmpty(from) ? LocalDate.parse(from, DateTimeFormatter.ISO_DATE) : null;
        LocalDate toLocalDate = !StringUtils.isEmpty(to) ? LocalDate.parse(to, DateTimeFormatter.ISO_DATE) : null;

        List<MealModel> mealEntities;

        if (date != null) {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            mealEntities = mealService.getMealsByUser(userId, localDate);
        } else {
            if (fromLocalDate != null && toLocalDate != null) {
                if (toLocalDate.isBefore(fromLocalDate)) {
                    throw new IllegalArgumentException("Invalid request from < to");
                }
                mealEntities = mealService.getMealsByUser(userId, fromLocalDate, toLocalDate);
            } else {
                mealEntities = mealService.getMealsByUser(userId);
            }
        }


        return mealEntities;
    }

    @Override
    @RequestMapping(value = "meals", method = RequestMethod.POST)
    public MealModel addMeals(@RequestBody AddMealRequest request) throws KaloriesException {
        MealModel mealToAdd = mealDtoModelMapper.map(request.getMeal());
        Long userId = request.getUserId();
        return mealService.createOrUpdateMealForUser(userId, mealToAdd);
    }

    @Override
    @RequestMapping(value = "meals/{id}", method = RequestMethod.PATCH)
    public MealModel updateMeal(@RequestBody AddMealRequest request, @PathVariable(name = "id") long id) throws KaloriesException {
        MealModel mealToUpdate = mealDtoModelMapper.map(request.getMeal());
        mealToUpdate.setId(id);
        Long userId = request.getUserId();
        return mealService.createOrUpdateMealForUser(userId, mealToUpdate);
    }

    @Override
    @Transactional
    @RequestMapping(value = "meals/{id}", method = RequestMethod.DELETE)
    public void deleteMeal(@PathVariable(name = "id") long id) throws KaloriesException {
        mealService.deleteMealById(id);
    }

}
