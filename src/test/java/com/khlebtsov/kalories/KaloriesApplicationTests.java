package com.khlebtsov.kalories;

import com.khlebtsov.kalories.controller.dto.request.AddMealRequest;
import com.khlebtsov.kalories.controller.dto.response.CaloriesCountResponse;
import com.khlebtsov.kalories.controller.dto.request.MealDto;
import com.khlebtsov.kalories.db.CaloriesPerUserRepository;
import com.khlebtsov.kalories.model.MealModel;
import com.khlebtsov.kalories.db.MealRepository;
import com.khlebtsov.kalories.db.UserMealRepository;
import com.khlebtsov.kalories.db.entity.UserMealEntity;
import com.khlebtsov.kalories.exception.KaloriesException;
import com.khlebtsov.kalories.service.CaloriesService;
import com.khlebtsov.kalories.service.MealService;
import com.khlebtsov.kalories.util.JsonUtil;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KaloriesApplicationTests {

    public static final String URL_GO = "/go";
    public static final String URL_MEALS = "/meals";
    public static final String URL_CALORIES = "/calories/count";

    @Autowired
    protected WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        mockMvc = builder.build();
    }

    @Test
    public void mealsFromAndToSet() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_MEALS)
                        .contentType(MediaType.APPLICATION_JSON);
        builder.param("userId", "1");
        builder.param("from", "2018-04-01");
        builder.param("to", LocalDate.now().format(DateTimeFormatter.ISO_DATE));

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        MealModel[] mealModels = JsonUtil.toObject(contentAsString, MealModel[].class);
        Assert.assertNotNull(mealModels);
        Assert.assertTrue(mealModels.length != 0);
    }

    @Test
    public void mealsFromSet() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_MEALS)
                        .contentType(MediaType.APPLICATION_JSON);
        builder.param("userId", "1");
        builder.param("from", "2018-04-01");

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        MealModel[] mealModels = JsonUtil.toObject(contentAsString, MealModel[].class);
        Assert.assertNotNull(mealModels);
        Assert.assertTrue(mealModels.length != 0);
    }

    @Test
    public void mealsToSet() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_MEALS)
                        .contentType(MediaType.APPLICATION_JSON);
        builder.param("userId", "1");
        builder.param("to", LocalDate.now().format(DateTimeFormatter.ISO_DATE));

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        MealModel[] mealModels = JsonUtil.toObject(contentAsString, MealModel[].class);
        Assert.assertNotNull(mealModels);
        Assert.assertTrue(mealModels.length != 0);
    }


    @Test
    public void meals() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_MEALS)
                        .contentType(MediaType.APPLICATION_JSON);
        builder.param("userId", "1");

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        MealModel[] mealModels = JsonUtil.toObject(contentAsString, MealModel[].class);
        Assert.assertNotNull(mealModels);
        Assert.assertTrue(mealModels.length != 0);
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
        MealModel[] mealModels = JsonUtil.toObject(contentAsString, MealModel[].class);
        Assert.assertNotNull(mealModels);
        Assert.assertTrue(mealModels.length != 0);
    }

    @Test
    public void addMeal() throws Exception {

        AddMealRequest request = new AddMealRequest();

        MealDto meal = new MealDto();
        meal.setText("new meal");
        meal.setNumberOfCalories(100);

        request.setMeal(meal);


        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post(URL_MEALS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(request));

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();

    }

    @Test
    public void deleteMeal() throws Exception {

        addMeal();

        ResultActions resultActions = this.mockMvc.perform(delete(URL_MEALS + "/{id}", 3));
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();

    }


    @Test
    public void caloriesCount() throws Exception {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get(URL_CALORIES)
                        .contentType(MediaType.APPLICATION_JSON);
        builder.param("userId", "1");
        builder.param("date", "2018-04-08");

        ResultActions resultActions = this.mockMvc.perform(builder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        CaloriesCountResponse caloriesCountResponse = JsonUtil.toObject(contentAsString, CaloriesCountResponse.class);
        Assert.assertNotNull(caloriesCountResponse);

    }


    @Autowired
    private UserMealRepository userMealRepository;

    @Autowired
    private MealService mealService;

    @Autowired
    private CaloriesPerUserRepository caloriesPerUserRepository;

    @Autowired
    private CaloriesService caloriesService;


    @Autowired
    private MealRepository mealRepository;

    @Test
    @Transactional
    public void repositoryTest() throws KaloriesException {

        //update
        MealModel mealModel = new MealModel(1L, "meat1", LocalDateTime.now(), 11L);
        mealService.createOrUpdateMealForUser(1L, mealModel);
        Optional<UserMealEntity> afterUpdate = userMealRepository.findById(1L);
        Assert.assertTrue(Objects.equals(mealModel.getText(), afterUpdate.get().getMeal().getText()));
        Assert.assertTrue(Objects.equals(mealModel.getNumberOfCalories(), afterUpdate.get().getMeal().getNumberOfCalories()));
        Assert.assertTrue(Objects.equals(mealModel.getTimestamp(), afterUpdate.get().getUpdatedAt()));


        //insert

        MealModel mealModelToInsert = new MealModel("meat2", 12L);
        MealModel afterInsertModel = mealService.createOrUpdateMealForUser(1L, mealModelToInsert);
        Optional<UserMealEntity> afterInsert = userMealRepository.findById(afterInsertModel.getId());
        Assert.assertTrue(Objects.equals(mealModelToInsert.getText(), afterInsert.get().getMeal().getText()));
        Assert.assertTrue(Objects.equals(mealModelToInsert.getNumberOfCalories(), afterInsert.get().getMeal().getNumberOfCalories()));
        Assert.assertTrue(Objects.equals(afterInsertModel.getTimestamp(), afterInsert.get().getUpdatedAt()));


//        mealRepository.findById(1L)
//        Optional<MealEntity> meat = mealRepository.findByTextAndNumberOfCalories("meat",10L);

//        caloriesService.setForUser(2L, 300L);
//        Optional<Long> caloriesForUser = caloriesService.getCaloriesForUser(2L);
//        Optional<CaloriesPerUserEntity> byUserId = caloriesPerUserRepository.findByUserId(1L);

//        List<MealModel> mealsByUser = mealService.getMealsByUser(1L);
//        Assert.assertNotNull(mealsByUser);
//        Assert.assertTrue(!mealsByUser.isEmpty());

//        List<UserMealEntity> meals = userMealRepository.getMeals(1L, Date.valueOf((LocalDate.now().withDayOfMonth(1))), Date.valueOf(LocalDate.now()));


//        List<UserMealEntity> userMealEntities = userMealRepository.findByUserId(1L);
//        userMealEntities.forEach(userMealEntity -> {
//            MealEntity meal = userMealEntity.getMeal();
//            UserEntity user = userMealEntity.getUser();
//        });
    }
}
