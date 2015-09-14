package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.base.UserMealBase;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Valk on 11.09.15.
 */
public class UserMealDaoInMemoryImp implements UserMealDao {

    @Override
    public List<UserMealWithExceed> read() {
        List<UserMealWithExceed> result;
        try {
            result = UserMealsUtil.getFilteredMealsWithExceeded(UserMealBase.getMealList(),
                    LocalTime.of(0, 0),
                    LocalTime.of(23, 59),
                    UserMealBase.getProfileById(1).getCaloriesPerDay());
        } catch (Exception e) {
            result = new ArrayList<>();
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public UserMeal read(LocalDateTime dateDateTime) {
        return UserMealsUtil.getUserMealByKey(UserMealBase.getMealList(), dateDateTime);
    }

    @Override
    public UserMeal read(Long id) {
        return UserMealsUtil.getUserMealById(UserMealBase.getMealList(), id);
    }

    @Override
    public List<UserMealWithExceed> read(Map<String, String[]> map) {
        LocalDate startDate = map.get("startDate")==null || map.get("startDate")[0].isEmpty() ? null : LocalDate.parse(map.get("startDate")[0]);
        LocalDate endDate = map.get("endDate")==null || map.get("endDate")[0].isEmpty() ? null : LocalDate.parse(map.get("endDate")[0]);
        LocalTime startTime = map.get("startTime")==null || map.get("startTime")[0].isEmpty() ? null : LocalTime.parse(map.get("startTime")[0]);
        LocalTime endTime = map.get("endTime")==null || map.get("endTime")[0].isEmpty() ? null : LocalTime.parse(map.get("endTime")[0]);
        List<UserMealWithExceed> result;
        try {
            result = UserMealsUtil.getFilteredMealsWithExceeded(UserMealBase.getMealList(),
                    startDate, endDate,
                    startTime, endTime,
                    UserMealBase.getProfileById(1).getCaloriesPerDay());
        } catch (Exception e) {
            result = new ArrayList<>();
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public UserMeal create(UserMeal userMeal) {
        return UserMealBase.appendUserMeal(userMeal);
    }

    @Override
    public UserMeal update(UserMeal userMeal) {
        return UserMealBase.putUserMeal(userMeal);
    }

    @Override
    public boolean delete(Long id) {
        return UserMealBase.deleteUserMeal(id);
    }

}
