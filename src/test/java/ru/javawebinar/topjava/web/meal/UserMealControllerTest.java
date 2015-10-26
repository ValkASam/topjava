package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by Valk on 24.10.15.
 */
public class UserMealControllerTest extends AbstractControllerTest {
    @Test
    public void testUserMealGetList() throws Exception {
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("mealList"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealList.jsp"))
                .andExpect(model().attribute("mealList", equalTo(
                        UserMealsUtil.getWithExceeded(USER_MEALS, LoggedUser.getCaloriesPerDay())
                )));
    }


    @Test
    public void testUserMealDelete() throws Exception {
        /*тупо в лоб: mockMvc.perform(get("/meals/delete?id="+MEAL1_ID))*/
        String mealPage = mockMvc.perform(get("/meals/delete").param("id", String.valueOf(MEAL1_ID)))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/meals"))
                .andReturn().getResponse().getRedirectedUrl();
        mockMvc.perform(get(mealPage))
                .andExpect(model().attribute("mealList", hasSize(5)));
    }

    @Test
    public void testUserMealCreate() throws Exception {
        mockMvc.perform(get("/meals/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("mealEdit"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealEdit.jsp"))
                .andExpect(model().attribute("meal", instanceOf(UserMeal.class)))
                .andExpect(model().attribute("meal", hasProperty("id", isEmptyOrNullString())
                ));
        UserMeal newMeal = getCreated();
        mockMvc.perform(post("/meals")
                        .param("calories", String.valueOf(newMeal.getCalories()))
                        .param("dateTime", String.valueOf(newMeal.getDateTime()))
                        .param("description", newMeal.getDescription())
                        .param("id", "")
        )
                .andExpect(status().isFound());
        mockMvc.perform(get("/meals"))
                .andExpect(model().attribute("mealList", hasSize(7)))
                .andExpect(model().attribute("mealList", hasItem(
                        allOf(
                                hasProperty("calories", is(newMeal.getCalories())),
                                hasProperty("dateTime", is(newMeal.getDateTime())),
                                hasProperty("description", is(newMeal.getDescription()))
                        )
                )));
    }

    @Test
    public void testUserMealUpdate() throws Exception {
        UserMeal updatedMeal = getUpdated();
        mockMvc.perform(get("/meals/update").param("id", String.valueOf(updatedMeal.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("mealEdit"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealEdit.jsp"))
                .andExpect(model().attribute("meal", is(updatedMeal)
                ));
        mockMvc.perform(post("/meals")
                        .param("id", String.valueOf(updatedMeal.getId()))
                        .param("calories", String.valueOf(updatedMeal.getCalories()))
                        .param("dateTime", String.valueOf(updatedMeal.getDateTime()))
                        .param("description", updatedMeal.getDescription())
        )
                .andExpect(status().isFound());
        mockMvc.perform(get("/meals"))
                .andExpect(model().attribute("mealList", hasSize(6)))
                .andExpect(model().attribute("mealList", hasItem(
                        allOf(
                                hasProperty("calories", is(updatedMeal.getCalories())),
                                hasProperty("dateTime", is(updatedMeal.getDateTime())),
                                hasProperty("description", is(updatedMeal.getDescription()))
                        )
                )));
    }

    @Test
    public void testUserMealFilter() throws Exception {
        mockMvc.perform(post("/meals/filter")
                        .param("startDate", LocalDate.of(2015, Month.MAY, 30).toString())
                        .param("endDate", LocalDate.of(2015, Month.MAY, 30).toString())
                        .param("startTime", LocalTime.MIN.toString())
                        .param("endTime", LocalTime.MAX.toString())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("mealList"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealList.jsp"))
                .andExpect(model().attribute("mealList", hasSize(3)))
                .andExpect(model().attribute("mealList", hasItem(hasProperty("id", is(MEAL1.getId())))))
                .andExpect(model().attribute("mealList", hasItem(hasProperty("id", is(MEAL2.getId())))))
                .andExpect(model().attribute("mealList", hasItem(hasProperty("id", is(MEAL3.getId())))));
    }

}


