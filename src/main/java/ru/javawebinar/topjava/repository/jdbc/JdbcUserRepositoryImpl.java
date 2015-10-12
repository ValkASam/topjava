package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private static final RowMapper<UserMeal> MEAL_ROW_MAPPER =
            (rs, rowNum) ->
                    new UserMeal(rs.getInt("id"), rs.getTimestamp("date_time").toLocalDateTime(),
                            rs.getString("description"), rs.getInt("calories"));

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;
    private SimpleJdbcInsert insertUserMeal;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("id");
        this.insertUserMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("MEALS");
    }

    @Override
    @Transactional
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay());

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            user.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);

            if (user.getMeals() != null) { //логика такая:
            // список еды не может быть == null в нормальных условиях.
            // Если все же он == null, то считаем, что модель не предполагает
            // поддержку целостности (для упрощенных, без учета Optional, тестов)
                jdbcTemplate.update("DELETE FROM meals WHERE user_id=?", user.getId());
                //int r = 3/0; //тест отката транзакции
                for (UserMeal meal : user.getMeals()) {
                    MapSqlParameterSource mealmap = new MapSqlParameterSource()
                            .addValue("id", meal.getId())
                            .addValue("description", meal.getDescription())
                            .addValue("calories", meal.getCalories())
                            .addValue("date_time", Timestamp.valueOf(meal.getDateTime()))
                            .addValue("user_id", user.getId());
                    insertUserMeal.execute(mealmap);
                }
            }
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
    }

    @Override
    public Collection<User> getAllWithMeals() {
        List<User> users = getAll();
        users.forEach(u -> {
            List<UserMeal> meals = jdbcTemplate.query(
                    "SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC", MEAL_ROW_MAPPER, u.getId());
            u.setMeals(meals);
        });
        return users;
    }

    @Override
    public User getWithMeals(int id) {
        User user = get(id);
        List<UserMeal> meals = jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC", MEAL_ROW_MAPPER, id);
        meals.forEach(m->m.setUser(user));
        user.setMeals(meals);
        return user;
    }


    public User updateLazy(User user){
        return save(user);
    }
}
