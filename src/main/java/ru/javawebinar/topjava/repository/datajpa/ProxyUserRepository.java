package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Transactional(readOnly = true)
public interface ProxyUserRepository extends JpaRepository<User, Integer> {

    public static final Sort SORT_NAME_EMAIL = new Sort("name", "email");

    @Transactional
    @Modifying
    @Query(name = User.DELETE)
    int delete(@Param("id") int id);

    @Override
    @Transactional
    User save(User user);

    @Override
    User findOne(Integer id);

    @Override
    List<User> findAll(Sort sort);

    User getByEmail(String email);

    @Query("SELECT DISTINCT(u) FROM User u JOIN FETCH u.meals ORDER BY u.name, u.email")
    public List<User> getAllWithMeals();

    /*
    Вариант: без @Query
    реализовано в интерфейсе, чтобы оставаться в контексте текущей Session, что необходимо для
    возможности дергать ленивый прокси-объект*//*
    default public Collection<User> getAllWithMeals() {
        List<User> users = findAll(SORT_NAME_EMAIL);
        users.forEach(u -> u.getMeals().size());
        return users;
    }*/

    @Query("SELECT DISTINCT(u) FROM User u JOIN FETCH u.meals WHERE u.id = ?1")
    public User getWithMeals(int id);

    /*Вариант: без @Query
    реализовано в интерфейсе, чтобы оставаться в контексте текущей Session, что необходимо для
    возможности дергать ленивый прокси-объект*/
    /*default public User getWithMeals(int id) {
        User user = findOne(id);
        user.getMeals().size();
        return user;
    }*/

    @Modifying
    @Transactional
    @Query("UPDATE User u SET " +
            " u.name = ?2," +
            " u.email = ?3," +
            " u.password = ?4," +
            " u.enabled = ?5," +
            " u.registered = ?6," +
            " u.caloriesPerDay = ?7 " +
            " WHERE u.id=?1")
    public int save(Integer id, String name, String email, String password, Boolean enabled, Date registered, Integer caloriesPerDay);
}