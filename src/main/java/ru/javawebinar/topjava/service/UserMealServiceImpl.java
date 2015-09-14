package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.UserMealDaoInMemoryImpl;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by Valk on 11.09.15.
 */
public class UserMealServiceImpl implements UserMealService {
    private static UserMealDaoInMemoryImpl userMealDaoInMemory = new UserMealDaoInMemoryImpl();

    @Override
    public List<UserMealWithExceed> read() {
        return userMealDaoInMemory.read();
    }

    @Override
    public UserMeal read(LocalDateTime dateTime) {
        return userMealDaoInMemory.read(dateTime);
    }

    @Override
    public UserMeal read(Long id) {
        return userMealDaoInMemory.read(id);
    }

    @Override
    public List<UserMealWithExceed> read(Map<String, String[]> map) {
        return userMealDaoInMemory.read(map);
    }

    @Override
    public UserMeal create(UserMeal userMeal) {
        return userMealDaoInMemory.create(userMeal);
    }

    @Override
    public UserMeal update(UserMeal userMeal) {
        return userMealDaoInMemory.update(userMeal);
    }

    @Override
    public boolean delete(Long id) {
        return userMealDaoInMemory.delete(id);
    }


}
