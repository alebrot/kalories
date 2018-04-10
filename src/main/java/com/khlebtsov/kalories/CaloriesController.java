package com.khlebtsov.kalories;


import com.khlebtsov.kalories.service.CaloriesFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class CaloriesController {

    private static final String INVALID_REQUEST_FROM_TO = "Invalid request from < to";
    private final CaloriesFacade caloriesFacade;

    @Autowired
    public CaloriesController(CaloriesFacade caloriesFacade) {
        this.caloriesFacade = caloriesFacade;
    }

    @RequestMapping(value = "calories/count", method = RequestMethod.GET)
    public int caloriesCount(
            @RequestParam Long userId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String date) {

        LocalDate fromLocalDate = !StringUtils.isEmpty(from) ? LocalDate.parse(from, DateTimeFormatter.ISO_DATE) : null;
        LocalDate toLocalDate = !StringUtils.isEmpty(to) ? LocalDate.parse(to, DateTimeFormatter.ISO_DATE) : null;

        int numberOfCalories;

        if (date != null) {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            numberOfCalories = caloriesFacade.getCaloriesByUser(userId, localDate);
        } else {
            if (fromLocalDate != null && toLocalDate != null) {
                if (toLocalDate.isBefore(fromLocalDate)) {
                    throw new IllegalArgumentException(INVALID_REQUEST_FROM_TO);
                }
                numberOfCalories = caloriesFacade.getCaloriesByUser(userId, fromLocalDate, toLocalDate);
            } else {
                numberOfCalories = caloriesFacade.getCaloriesByUser(userId);
            }
        }

        return numberOfCalories;
    }


}
