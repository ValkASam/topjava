package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by Valk on 11.09.15.
 */
public interface UserMealService {
    List<UserMealWithExceed> read();
    UserMeal read(LocalDateTime dateTime);
    UserMeal read(Long id);
    List<UserMealWithExceed>  read(Map<String, String[]> map);
    UserMeal create(UserMeal userMeal);
    UserMeal update (UserMeal userMeal);
    boolean delete (Long id);
}
