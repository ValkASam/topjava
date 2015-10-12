package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.Collection;
import java.util.Map;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public interface UserRepository {
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    Collection<User> getAll();


    //методы загрузки пользователей вместе с едой с учетом, что может быть FetchType.LAZY
    //для jdbc, jpa, datajpa своя реализация
    public Collection<User> getAllWithMeals();

    public User getWithMeals(int id);

    public User updateLazy(User user);
}
