package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 24.09.2015.
 */
public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);

    public static final List<User> USERS = Arrays.asList(ADMIN, USER);

    public static final List<User> USERS_DEEP = USERS
            .stream()
            .map(UserTestData::addMeals)
            .collect(Collectors.toList());

    public static User addMeals(User user) {
        User u = new User(user);
        //оригинальный код утерян. Н был закомитен. Начиная с ветки 5.1 появляется, но сюда не подходит
        return u;
    }
}
