package com.khlebtsov.kalories.layers.controller.impl;


import com.khlebtsov.kalories.layers.controller.CaloriesController;
import com.khlebtsov.kalories.layers.controller.dto.response.CaloriesCountResponse;
import com.khlebtsov.kalories.layers.controller.dto.request.SetCaloriesRequest;
import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.layers.facade.impl.CaloriesFacadeDefault;
import com.khlebtsov.kalories.layers.service.impl.CaloriesServiceDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
public class CaloriesControllerDefault implements CaloriesController {

    private static final String INVALID_REQUEST_FROM_TO = "Invalid request from < to";
    private final CaloriesFacadeDefault caloriesFacade;
    private final CaloriesServiceDefault caloriesService;

    @Autowired
    public CaloriesControllerDefault(CaloriesFacadeDefault caloriesFacade, CaloriesServiceDefault caloriesService) {
        this.caloriesFacade = caloriesFacade;
        this.caloriesService = caloriesService;
    }

    @Override
    @RequestMapping(value = "calories/count", method = RequestMethod.GET)
    public CaloriesCountResponse caloriesCount(
            @RequestParam Long userId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String date) throws KaloriesException {

        LocalDate fromLocalDate = !StringUtils.isEmpty(from) ? LocalDate.parse(from, DateTimeFormatter.ISO_DATE) : null;
        LocalDate toLocalDate = !StringUtils.isEmpty(to) ? LocalDate.parse(to, DateTimeFormatter.ISO_DATE) : null;

        Optional<Pair<Long, CaloriesFacadeDefault.CaloriesStatus>> calories;
        if (date != null) {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            calories = caloriesFacade.getCaloriesByUser(userId, localDate);
        } else {
            if (fromLocalDate != null && toLocalDate != null) {
                if (toLocalDate.isBefore(fromLocalDate)) {
                    throw new IllegalArgumentException(INVALID_REQUEST_FROM_TO);
                }
                calories = caloriesFacade.getCaloriesByUser(userId, fromLocalDate, toLocalDate);
            } else {
                calories = caloriesFacade.getCaloriesByUser(userId);
            }
        }

        if (!calories.isPresent()) {
            throw new KaloriesException("Calories sum not found for userId " + userId);
        }


        CaloriesCountResponse caloriesCountResponse = new CaloriesCountResponse();
        caloriesCountResponse.setCaloriesStatus(calories.get().getSecond());
        caloriesCountResponse.setCaloriesCount(calories.get().getFirst());

        return caloriesCountResponse;
    }

    @Override
    @RequestMapping(value = "calories", method = RequestMethod.POST)
    public void setCaloriesForUser(@RequestBody @Valid SetCaloriesRequest request) throws KaloriesException {
        Long calories = request.getCalories();
        Long userId = request.getUserId();
        caloriesService.setForUser(userId, calories);
    }

}
