package ru.javawebinar.topjava.service;


import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public interface UserService {

    public User save(User user);

    public void delete(int id) throws NotFoundException;

    public User get(int id) throws NotFoundException;

    Collection<User> getAll();

    public User getByEmail(String email) throws NotFoundException;

    void update(User user) throws NotFoundException;
    

}
