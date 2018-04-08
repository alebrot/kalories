package com.khlebtsov.kalories;

import com.khlebtsov.kalories.entity.Meal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KaloriesApplicationTests {

    public static final String URL_GO = "/go";
    public static final String URL_MEALS = "/meals";

    @Autowired
    protected WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        mockMvc = builder.build();
    }

    @Test
    public void contextLoads() throws Exception {

//http://localhost:8080/meals?from=2018-04-08&to=2018-04-08
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_GO)
                        .contentType(MediaType.APPLICATION_JSON);
        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();

        Meal[] meals = JsonUtil.toObject(contentAsString, Meal[].class);
    }


    @Test
    public void mealsFromAndToSet() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_MEALS)
                        .contentType(MediaType.APPLICATION_JSON);
        builder.param("from", "2018-04-08");
        builder.param("to", LocalDate.now().format(DateTimeFormatter.ISO_DATE));

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        Meal[] meals = JsonUtil.toObject(contentAsString, Meal[].class);
        Assert.assertNotNull(meals);
        Assert.assertEquals(2, meals.length);
    }

    @Test
    public void mealsFromSet() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_MEALS)
                        .contentType(MediaType.APPLICATION_JSON);
        builder.param("from", "2018-04-08");

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        Meal[] meals = JsonUtil.toObject(contentAsString, Meal[].class);
        Assert.assertNotNull(meals);
        Assert.assertEquals(2, meals.length);
    }

    @Test
    public void mealsToSet() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_MEALS)
                        .contentType(MediaType.APPLICATION_JSON);
        builder.param("to", LocalDate.now().format(DateTimeFormatter.ISO_DATE));

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        Meal[] meals = JsonUtil.toObject(contentAsString, Meal[].class);
        Assert.assertNotNull(meals);
        Assert.assertEquals(2, meals.length);
    }


    @Test
    public void meals() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_MEALS)
                        .contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        Meal[] meals = JsonUtil.toObject(contentAsString, Meal[].class);
        Assert.assertNotNull(meals);
        Assert.assertEquals(2, meals.length);
    }

    @Test
    public void mealsDateSet() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_MEALS)
                        .contentType(MediaType.APPLICATION_JSON);
        builder.param("date", "2018-04-08");

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        Meal[] meals = JsonUtil.toObject(contentAsString, Meal[].class);
        Assert.assertNotNull(meals);
        Assert.assertEquals(2, meals.length);
    }
}
