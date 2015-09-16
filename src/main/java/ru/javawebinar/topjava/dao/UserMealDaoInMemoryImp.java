package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.base.UserMealBase;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Valk on 11.09.15.
 */
public class UserMealDaoInMemoryImp implements UserMealDao {

    @Override
    public List<UserMealWithExceed> getAll() {
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
    public UserMeal getById(Long id) {
        return getUserMealById(UserMealBase.getMealList(), id);
    }

    @Override
    public List<UserMealWithExceed> getByDateTime(Map<String, String[]> map) {
        LocalDate startDate = map.get("startDate")==null || map.get("startDate")[0].isEmpty() ? null : LocalDate.parse(map.get("startDate")[0]);
        LocalDate endDate = map.get("endDate")==null || map.get("endDate")[0].isEmpty() ? null : LocalDate.parse(map.get("endDate")[0]);
        LocalTime startTime = map.get("startTime")==null || map.get("startTime")[0].isEmpty() ? null : LocalTime.parse(map.get("startTime")[0]);
        LocalTime endTime = map.get("endTime")==null || map.get("endTime")[0].isEmpty() ? null : LocalTime.parse(map.get("endTime")[0]);
        List<UserMealWithExceed> result;
        try {
            result = getFullFilteredMealsWithExceeded(UserMealBase.getMealList(),
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

    /*утилитные методы*/
    private static List<UserMealWithExceed> getFullFilteredMealsWithExceeded(List<UserMeal> mealList, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream().collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime(), startDate, endDate, startTime, endTime))
                .map(um -> new UserMealWithExceed(um.getId(), um.getDateTime(), um.getDescription(), um.getCalories(),
                        caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static UserMeal getUserMealByKey(List<UserMeal> mealList, LocalDateTime key) {
        return mealList.stream()
                .filter(um -> um.getDateTime().isEqual(key))
                .findFirst().get();
    }

    public static UserMeal getUserMealById(List<UserMeal> mealList, Long id) {
        try {
            return mealList.stream()
                    .filter(um -> um.getId().equals(id))
                    .findFirst().get();
        } catch (Exception e) { //NoSuchElementException ожидается
            try {
                UserMeal um = new UserMeal(LocalDateTime.now(), "", UserMealBase.getProfileById(1).getCaloriesPerDay());
                return um;
            } catch (Exception e1) {
                e1.printStackTrace();
                return new UserMeal(LocalDateTime.now(), "", 0);
            }
        }
    }

}
