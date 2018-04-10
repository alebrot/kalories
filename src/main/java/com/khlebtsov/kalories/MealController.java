package com.khlebtsov.kalories;


import com.khlebtsov.kalories.dto.AddMealRequest;
import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.mapper.MealDtoModelMapper;
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
    private final MealDtoModelMapper mealDtoModelMapper;

    @Autowired
    public MealController(MealService mealService, MealDtoModelMapper mealDtoModelMapper) {
        this.mealService = mealService;
        this.mealDtoModelMapper = mealDtoModelMapper;
    }


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

    @RequestMapping(value = "meals", method = RequestMethod.POST)
    public MealModel addMeals(@RequestBody AddMealRequest request) throws KaloriesException {
        MealModel mealToAdd = mealDtoModelMapper.map(request.getMeal());
        Long userId = request.getUserId();
        return mealService.createOrUpdateMealForUser(userId, mealToAdd);
    }

    @RequestMapping(value = "meals/{id}", method = RequestMethod.PATCH)
    public MealModel updateMeal(@RequestBody AddMealRequest request, @PathVariable(name = "id") long id) throws KaloriesException {
        MealModel mealToUpdate = mealDtoModelMapper.map(request.getMeal());
        mealToUpdate.setId(id);
        Long userId = request.getUserId();
        return mealService.createOrUpdateMealForUser(userId, mealToUpdate);
    }

    @Transactional
    @RequestMapping(value = "meals/{id}", method = RequestMethod.DELETE)
    public MealModel meals(@PathVariable(name = "id") long id) {
        Optional<MealModel> meal = mealService.getMeal(id);
        if (!meal.isPresent()) {
            throw new IllegalArgumentException("MealEntity does'n exist");
        }
        MealModel mealModel = meal.get();
        mealService.deleteMeal(mealModel);
        return mealModel;
    }

}
