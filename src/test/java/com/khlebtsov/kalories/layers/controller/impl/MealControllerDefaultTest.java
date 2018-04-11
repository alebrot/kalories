package com.khlebtsov.kalories.layers.controller.impl;

import com.khlebtsov.kalories.db.entity.MealEntity;
import com.khlebtsov.kalories.db.entity.UserMealEntity;
import com.khlebtsov.kalories.layers.controller.dto.request.AddMealRequest;
import com.khlebtsov.kalories.layers.controller.dto.request.MealDto;
import com.khlebtsov.kalories.layers.service.impl.DefaultTestData;
import com.khlebtsov.kalories.layers.service.impl.MealServiceDefault;
import com.khlebtsov.kalories.mapper.MealDtoModelMapper;
import com.khlebtsov.kalories.model.MealModel;
import com.khlebtsov.kalories.util.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class MealControllerDefaultTest extends DefaultTestData {

    private static final String URL_MEALS = "/meals";

    @Autowired
    protected WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Override
    public void setup() {
        super.setup();
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        mockMvc = builder.build();
    }
    @Transactional
    @Test
    public void meals() throws Exception {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_MEALS)
                        .contentType(MediaType.APPLICATION_JSON);
        builder.param("userId", userEntity.getId().toString());

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        MealModel[] mealModels = JsonUtil.toObject(contentAsString, MealModel[].class);
        Assert.assertNotNull(mealModels);
        Assert.assertEquals(3, mealModels.length);
        Assert.assertEquals(mealModels[0].getId(), userMealEntity1.getId());
        Assert.assertEquals(mealModels[1].getId(), userMealEntity2.getId());
        Assert.assertEquals(mealModels[2].getId(), userMealEntity3.getId());
    }
    @Transactional
    @Test
    public void mealsDateSet() throws Exception {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_MEALS)
                        .contentType(MediaType.APPLICATION_JSON);
        builder.param("userId", userEntity.getId().toString());
        builder.param("date", userMealEntity1.getUpdatedAt().format(DateTimeFormatter.ISO_DATE));

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        MealModel[] mealModels = JsonUtil.toObject(contentAsString, MealModel[].class);
        Assert.assertNotNull(mealModels);
        Assert.assertEquals(1, mealModels.length);
        Assert.assertEquals(mealModels[0].getId(), userMealEntity1.getId());

    }
    @Transactional
    @Test
    public void mealsRangeSet() throws Exception {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_MEALS)
                        .contentType(MediaType.APPLICATION_JSON);
        builder.param("userId", userEntity.getId().toString());
        builder.param("from", userMealEntity1.getUpdatedAt().format(DateTimeFormatter.ISO_DATE));
        builder.param("to", userMealEntity2.getUpdatedAt().format(DateTimeFormatter.ISO_DATE));

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        MealModel[] mealModels = JsonUtil.toObject(contentAsString, MealModel[].class);
        Assert.assertNotNull(mealModels);
        Assert.assertEquals(2, mealModels.length);
        Assert.assertEquals(mealModels[0].getId(), userMealEntity1.getId());
        Assert.assertEquals(mealModels[1].getId(), userMealEntity2.getId());

    }
    @Transactional
    @Test
    public void addMeals() throws Exception {
        AddMealRequest addMealRequest = new AddMealRequest();

        MealDto meal = new MealDto();
        meal.setText("new meal");
        meal.setNumberOfCalories(100);
        addMealRequest.setMeal(meal);
        addMealRequest.setUserId(userEntity.getId());

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post(URL_MEALS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(addMealRequest));

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        MealModel mealModel = JsonUtil.toObject(contentAsString, MealModel.class);
        Assert.assertEquals(mealModel.getText(), meal.getText());
        Assert.assertNotNull(mealModel.getId());
        Assert.assertNotNull(mealModel.getTime());
        Assert.assertNotNull(mealModel.getDate());
        Assert.assertEquals(mealModel.getNumberOfCalories(), meal.getNumberOfCalories());
    }

    @Test
    @Transactional
    public void updateMeal() throws Exception {
        AddMealRequest addMealRequest = new AddMealRequest();

        MealDto meal = new MealDto();
        meal.setText("new meal");
        meal.setNumberOfCalories(100);
        addMealRequest.setMeal(meal);
        addMealRequest.setUserId(userEntity.getId());

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch(URL_MEALS + "/{id}", userMealEntity1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(addMealRequest));

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        MealModel mealModel = JsonUtil.toObject(contentAsString, MealModel.class);

        Optional<UserMealEntity> updated = userMealRepository.findById(userMealEntity1.getId());
        MealEntity mealUpdated = updated.get().getMeal();

        Assert.assertEquals(mealModel.getText(), mealUpdated.getText());
        Assert.assertEquals(mealModel.getId(), mealUpdated.getId());
        Assert.assertEquals(mealModel.getNumberOfCalories(), (long) mealUpdated.getNumberOfCalories());
        Assert.assertEquals(mealModel.getTimestamp(), updated.get().getUpdatedAt());
    }
    @Transactional
    @Test
    public void deleteMeal() throws Exception {
        this.mockMvc.perform(delete(URL_MEALS + "/{id}", userMealEntity1.getId()))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
        Optional<UserMealEntity> deleted = userMealRepository.findById(userMealEntity1.getId());
        Assert.assertFalse(deleted.isPresent());
    }
}