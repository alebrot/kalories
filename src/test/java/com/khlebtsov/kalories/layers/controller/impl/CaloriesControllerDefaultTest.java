package com.khlebtsov.kalories.layers.controller.impl;

import com.khlebtsov.kalories.db.entity.CaloriesPerUserEntity;
import com.khlebtsov.kalories.layers.controller.dto.request.SetCaloriesRequest;
import com.khlebtsov.kalories.layers.controller.dto.response.CaloriesCountResponse;
import com.khlebtsov.kalories.layers.service.impl.DefaultTestData;
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
import org.springframework.web.context.WebApplicationContext;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class CaloriesControllerDefaultTest extends DefaultTestData {

    public static final String URL_CALORIES_COUNT = "/calories/count";
    public static final String URL_SET_CALORIES_FOR_USER = "/calories";
    @Autowired
    protected WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Override
    public void setup() {
        super.setup();
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        mockMvc = builder.build();
    }

    @Test
    public void caloriesCount() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_CALORIES_COUNT)
                        .contentType(MediaType.APPLICATION_JSON);
        builder.param("userId", userEntity.getId().toString());
        builder.param("date", userMealEntity1.getUpdatedAt().format(DateTimeFormatter.ISO_DATE));

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        CaloriesCountResponse caloriesCountResponse = JsonUtil.toObject(contentAsString, CaloriesCountResponse.class);
        Assert.assertNotNull(caloriesCountResponse);
        Assert.assertEquals((long) mealEntity1.getNumberOfCalories(), (long) caloriesCountResponse.getCaloriesCount());
    }

    @Test
    public void setCaloriesForUser() throws Exception {

        long calories = 555L;
        SetCaloriesRequest setCaloriesRequest = new SetCaloriesRequest();
        setCaloriesRequest.setCalories(calories);
        setCaloriesRequest.setUserId(userEntity.getId());

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post(URL_SET_CALORIES_FOR_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(setCaloriesRequest));

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));

        Optional<CaloriesPerUserEntity> caloriesSet = caloriesPerUserRepository.findByUserId(userEntity.getId());
        Assert.assertTrue(caloriesSet.isPresent());
        Assert.assertEquals(calories, (long) caloriesSet.get().getNumberOfCalories());

    }
}