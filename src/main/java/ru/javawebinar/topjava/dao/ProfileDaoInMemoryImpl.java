package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.base.UserMealBase;
import ru.javawebinar.topjava.model.Profile;
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
public class ProfileDaoInMemoryImpl implements ProfileDao {
    @Override
    public Profile read(Long id) {
        return UserMealBase.getProfileById(id);
    }

    @Override
    public Profile update(Profile profile) {
        return UserMealBase.putProfile(profile);
    }
}
