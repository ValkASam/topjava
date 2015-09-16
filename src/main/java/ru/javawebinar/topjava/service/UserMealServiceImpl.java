package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.UserMealDaoInMemoryImp;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.List;
import java.util.Map;

/**
 * Created by Valk on 11.09.15.
 */
public class UserMealServiceImpl implements UserMealService {
    private static UserMealDaoInMemoryImp userMealDaoInMemory = new UserMealDaoInMemoryImp();

    @Override
    public List<UserMealWithExceed> getAll() {
        return userMealDaoInMemory.getAll();
    }

    @Override
    public UserMeal getById(Long id) {
        return userMealDaoInMemory.getById(id);
    }

    @Override
    public List<UserMealWithExceed> getByDateTime(Map<String, String[]> map) {
        return userMealDaoInMemory.getByDateTime(map);
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
