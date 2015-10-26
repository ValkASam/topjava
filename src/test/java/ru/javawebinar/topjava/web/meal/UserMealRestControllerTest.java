package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by Valk on 24.10.15.
 */
public class UserMealRestControllerTest extends AbstractControllerTest {
    public static final String REST_URL = UserMealRestController.REST_URL; // "/rest/meals";

    @Test
    public void testUserMealRestGet() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testUserMealRestGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MATCHER.contentListMatcher(USER_MEALS));
    }

    @Test
    public void testUserMealRestDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        List<UserMeal> modifiedMealList = new ArrayList<>(USER_MEALS);
        modifiedMealList.remove(MEAL1);
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(modifiedMealList));
    }

    @Test
    public void testUserMealRestCreate() throws Exception {
        UserMeal newMeal = getCreated();
        /*можно (не обязательно) разнести построение и выполнение реквеста*/
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(newMeal));
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andReturn().getResponse();
        mockMvc.perform(get(mockHttpServletResponse.getHeader("Location")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUserMealRestUpdate() throws Exception {
        UserMeal updatedMeal = getUpdated();
        mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.writeValue(updatedMeal))
        )
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(REST_URL + "/" + updatedMeal.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MATCHER.contentMatcher(updatedMeal));
    }

    @Test
    public void testUserMealRestFilter() throws Exception {
        mockMvc.perform(get(REST_URL + "/filter")
                        .param("startDate", LocalDate.of(2015, Month.MAY, 30).toString())
                        .param("endDate", LocalDate.of(2015, Month.MAY, 30).toString())
                        .param("startTime", LocalTime.MIN.toString())
                        .param("endTime", LocalTime.MAX.toString())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(Arrays.asList(MEAL3, MEAL2, MEAL1)));
    }

}



