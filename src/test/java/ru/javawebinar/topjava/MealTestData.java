package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */

public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Map<Integer, List<UserMeal>> MEAL_LIST = new HashMap<Integer, List<UserMeal>>(){{
        put(ADMIN_ID, new ArrayList<UserMeal>(){{
            add(new UserMeal(100009,LocalDateTime.parse("2015-10-30T21:00:00"),"Админ ужин",510));
            add(new UserMeal(100008,LocalDateTime.parse("2015-10-30T14:00:00"),"Админ ланч",510));
        }});
        put(USER_ID, new ArrayList<UserMeal>(){{
            add(new UserMeal(100004,LocalDateTime.parse("2015-09-30T20:00:00"),"Ужин",500));
            add(new UserMeal(100003,LocalDateTime.parse("2015-09-30T13:00:00"),"Обед",1000));
            add(new UserMeal(100002,LocalDateTime.parse("2015-09-30T10:00:00"),"Завтрак",500));
            add(new UserMeal(100007,LocalDateTime.parse("2015-09-29T20:00:00"),"Ужин",510));
            add(new UserMeal(100006,LocalDateTime.parse("2015-09-29T13:00:00"),"Обед",500));
            add(new UserMeal(100005,LocalDateTime.parse("2015-09-29T10:00:00"),"Завтрак",1000));
        }});
    }};

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);


}
