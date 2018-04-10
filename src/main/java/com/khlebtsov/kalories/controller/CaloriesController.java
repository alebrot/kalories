package com.khlebtsov.kalories.controller;

import com.khlebtsov.kalories.controller.dto.request.SetCaloriesRequest;
import com.khlebtsov.kalories.controller.dto.response.CaloriesCountResponse;
import com.khlebtsov.kalories.exception.KaloriesException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface CaloriesController {
    @RequestMapping(value = "calories/count", method = RequestMethod.GET)
    CaloriesCountResponse caloriesCount(
            @RequestParam Long userId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String date) throws KaloriesException;

    @RequestMapping(value = "calories", method = RequestMethod.POST)
    void setCaloriesForUser(@RequestBody @Valid SetCaloriesRequest request) throws KaloriesException;
}
