package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by Valk on 30.09.15.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.setSqlScriptEncoding("UTF-8");
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        for (Map.Entry<Integer, List<UserMeal>> pair : MEAL_LIST.entrySet()) {
            Integer userId = pair.getKey();
            for (UserMeal expectedMeal : pair.getValue()) {
                UserMeal actualMeal = service.get(expectedMeal.getId(), userId);
                MATCHER.assertEquals(expectedMeal, actualMeal);
            }
        }
    }

    @Test(expected = NotFoundException.class)
    public void testGetWrong() throws Exception {
        service.get(MEAL_LIST.get(USER_ID).get(0).getId(), USER_ID + 1);
    }

    @Test
    public void testDelete() throws Exception {
        for (UserMeal meal : MEAL_LIST.get(USER_ID)) {
            service.delete(meal.getId(), USER_ID);
        }
        Collection<UserMeal> list = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(), list); //еды USER_ID не должно остаться
        Collection<UserMeal> restList = service.getAll(ADMIN_ID);
        MATCHER.assertCollectionEquals(MEAL_LIST.get(ADMIN_ID), restList); //ADMIN_ID должен остаться нетронутым
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteWrong() throws Exception {
        service.delete(MEAL_LIST.get(USER_ID).get(0).getId(), USER_ID + 1);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        List<UserMeal> USERList = MEAL_LIST.get(USER_ID);
        Collection<UserMeal> allUSER = service.getBetweenDates(USERList.get(USERList.size() - 1).getDateTime().toLocalDate(), USERList.get(0).getDateTime().toLocalDate(), USER_ID);
        MATCHER.assertCollectionEquals(USERList, allUSER);
        //
        List<UserMeal> ADMINList = MEAL_LIST.get(ADMIN_ID);
        Collection<UserMeal> allADMIN = service.getBetweenDates(ADMINList.get(ADMINList.size() - 1).getDateTime().toLocalDate(), ADMINList.get(0).getDateTime().toLocalDate(), ADMIN_ID);
        MATCHER.assertCollectionEquals(ADMINList, allADMIN);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        List<UserMeal> USERList = MEAL_LIST.get(USER_ID);
        Collection<UserMeal> allUSER = service.getBetweenDateTimes(USERList.get(USERList.size() - 1).getDateTime(), USERList.get(0).getDateTime(), USER_ID);
        MATCHER.assertCollectionEquals(USERList, allUSER);
        //
        List<UserMeal> ADMINList = MEAL_LIST.get(ADMIN_ID);
        Collection<UserMeal> allADMIN = service.getBetweenDateTimes(ADMINList.get(ADMINList.size() - 1).getDateTime(), ADMINList.get(0).getDateTime(), ADMIN_ID);
        MATCHER.assertCollectionEquals(ADMINList, allADMIN);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<UserMeal> allUSER = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(MEAL_LIST.get(USER_ID), allUSER);
        Collection<UserMeal> allADMIN = service.getAll(ADMIN_ID);
        MATCHER.assertCollectionEquals(MEAL_LIST.get(ADMIN_ID), allADMIN);
    }

    @Test
    public void testUpdate() throws Exception {
        for (Map.Entry<Integer, List<UserMeal>> pair : MEAL_LIST.entrySet()) {
            Integer userId = pair.getKey();
            for (UserMeal meal : pair.getValue()) {
                UserMeal expectedMeal = new UserMeal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
                expectedMeal.setCalories(expectedMeal.getCalories() + 1);
                expectedMeal.setDescription(expectedMeal.getDescription() + "-updated");
                expectedMeal.setDateTime(expectedMeal.getDateTime().plusDays(1));
                UserMeal actualMeal = service.update(expectedMeal, userId);
                MATCHER.assertEquals(expectedMeal, actualMeal);
            }
        }
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateWrong() throws Exception {
        UserMeal meal = MEAL_LIST.get(USER_ID).get(0);
        service.update(meal, USER_ID + 1);
    }

    @Test
    public void testSave() throws Exception {
        UserMeal newMeal = new UserMeal(LocalDateTime.now(), "новая еда", 100);
        UserMeal savedMeal = service.save(newMeal, USER_ID);
        newMeal.setId(savedMeal.getId());
        MATCHER.assertEquals(newMeal, savedMeal);
    }
}