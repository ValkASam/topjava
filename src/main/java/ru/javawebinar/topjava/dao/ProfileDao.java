package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Profile;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by Valk on 11.09.15.
 */
public interface ProfileDao {
    Profile read(Long id);
    Profile update (Profile profile);
}
