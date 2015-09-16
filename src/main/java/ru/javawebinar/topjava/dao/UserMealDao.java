package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by Valk on 11.09.15.
 */
public interface UserMealDao {
    List<UserMealWithExceed> getAll();
    UserMeal getById(Long id);
    List<UserMealWithExceed> getByDateTime(Map<String, String[]> map);
    UserMeal create(UserMeal userMeal);
    UserMeal update (UserMeal userMeal);
    boolean delete(Long id);
}
