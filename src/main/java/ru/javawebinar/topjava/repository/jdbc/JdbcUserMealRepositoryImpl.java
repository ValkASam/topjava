package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    //private static final BeanPropertyRowMapper<UserMeal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(UserMeal.class);
    private static final BeanPropertyRowMapper<UserMeal> ROW_MAPPER = new BeanPropertyRowMapper<UserMeal>() {
        @Override
        public UserMeal mapRow(ResultSet rs, int rowNumber) throws SQLException {
            return new UserMeal(rs.getInt("id"),
                    rs.getTimestamp("datetime").toLocalDateTime(),
                    rs.getString("description"),
                    rs.getInt("calories"));
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUserMeal;

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.insertUserMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("datetime", Timestamp.valueOf(userMeal.getDateTime()))
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories())
                .addValue("user_id", userId);
        if (userMeal.isNew()) {
            userMeal.setId((Integer) insertUserMeal.executeAndReturnKey(mapSqlParameterSource));
        } else if (namedParameterJdbcTemplate.update(
                " UPDATE meals " +
                        " SET datetime = :datetime," +
                        "description = :description," +
                        "calories = :calories" +
                        " WHERE id = :id AND user_id = :user_id", mapSqlParameterSource) == 0) {
            return null;
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return DataAccessUtils.singleResult(jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?",
                ROW_MAPPER, id, userId));
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY datetime DESC",
                ROW_MAPPER, userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("start_date", Timestamp.valueOf(startDate))
                .addValue("end_date", Timestamp.valueOf(endDate));
        return namedParameterJdbcTemplate.query(
                "SELECT * " +
                        " FROM meals " +
                        " WHERE user_id = :user_id AND datetime >= :start_date AND datetime <= :end_date " +
                        " ORDER BY datetime DESC",
                mapSqlParameterSource, ROW_MAPPER);
    }
}
