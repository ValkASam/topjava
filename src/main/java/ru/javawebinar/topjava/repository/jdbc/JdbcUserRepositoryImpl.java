package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import java.util.*;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    //private static final BeanPropertyRowMapper<Role> ROLE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Role.class);
    private static final RowMapper<Role> ROLE_ROW_MAPPER =
            (rs, rowNum) -> Role.valueOf(rs.getString("role"));

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    private SimpleJdbcInsert insertUser;
    private SimpleJdbcInsert insertRoles;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("id");
        this.insertRoles = new SimpleJdbcInsert(dataSource)
                .withTableName("USER_ROLES");
    }

    @Override
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
            TransactionDefinition txDef = new DefaultTransactionDefinition();
            TransactionStatus txStatus = transactionManager.getTransaction(txDef);
            try {
                Number newKey = insertUser.executeAndReturnKey(map);
                user.setId(newKey.intValue());
                List<MapSqlParameterSource> roleMapList = new ArrayList<>();
                user.getRoles().forEach(r -> roleMapList.add(
                        new MapSqlParameterSource()
                                .addValue("user_id", user.getId()) //user.getId() + 100 - проверка отката транзакции
                                .addValue("role", r.name())));
                //int d = 1 / 0; //проверка отката транзакции
                int batchSize = user.getRoles().size();
                int[] res = insertRoles.executeBatch(
                        roleMapList.toArray(
                                new MapSqlParameterSource[batchSize]
                        )
                );
                //res[1] = 0; //проверка отката транзакции
                Arrays.sort(res);
                if (Arrays.binarySearch(res, 0) >= 0) {
                    throw new Exception("Ошибка сохранения: не удалось сохранить роли");
                }
                transactionManager.commit(txStatus);
            } catch (Exception e) {
                transactionManager.rollback(txStatus);
                throw new DataIntegrityViolationException(e.getMessage());
            }
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        //заботится о зависимостях не надо - есть ON DELETE CASCADE
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        users.forEach(u -> u.setRoles(fetchRoles(u.getId())));
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        user.setRoles(fetchRoles(user.getId()));
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        users.forEach(u -> u.setRoles(fetchRoles(u.getId())));
        return users;
    }

    private Set<Role> fetchRoles(Integer userId) {
        return new HashSet<Role>() {{
            addAll(jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id=?", ROLE_ROW_MAPPER, userId));
        }};
    }
}
