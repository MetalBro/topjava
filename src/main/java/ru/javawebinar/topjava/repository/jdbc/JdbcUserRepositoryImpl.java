package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static class MyRowMapper implements ResultSetExtractor<List<User>>{
    @Override
    public List<User> extractData(ResultSet resultSet) throws SQLException {
        Map<Integer, User> userMap = new HashMap<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            User user = userMap.get(id);
            if (user == null){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRegistered(resultSet.getDate("registered"));
                user.setEnabled(resultSet.getBoolean("enabled"));
                user.setCaloriesPerDay(resultSet.getInt("calories_per_day"));
                Set<Role> roleSet = new HashSet<>();
                roleSet.add(Role.valueOf(resultSet.getString("role")));
                user.setRoles(roleSet);
                userMap.put(id, user);
            } else {
                user.getRoles().add(Role.valueOf(resultSet.getString("role")));
            }
        }
        List<User> userList = new ArrayList<>(userMap.values());
        userList.sort((o1, o2) -> {
                int result = o1.getName().compareTo(o2.getName());
                if (result == 0){
                    result = o1.getEmail().compareTo(o2.getEmail());
                }
                return result;
        });
        return userList;
    }
}

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final SimpleJdbcInsert insertUserRoles;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.insertUserRoles = new SimpleJdbcInsert(dataSource)
                .withTableName("user_roles")
                .usingColumns("role", "user_id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        Map<String, Object> userRolesMap = new HashMap<>();
        Set<Role> roleSet = new HashSet<>(user.getRoles());
        Number newKey = null;
        if (user.isNew()) {
            newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());

        } else {
            newKey = user.getId();
            if (namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
                return null;
            }
        }
        int userId = newKey.intValue();
        roleSet.forEach(role -> {
                    userRolesMap.put("user_id", userId);
                    userRolesMap.put("role", role);
                }
        );
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", userId);
        insertUserRoles.execute(userRolesMap);
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users INNER JOIN user_roles ON user_roles.user_id = users.id WHERE id=?", new MyRowMapper(), id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
//        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users INNER JOIN user_roles ON user_roles.user_id = users.id WHERE email=?", new MyRowMapper(), email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
//        return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        return jdbcTemplate.query("SELECT * FROM users INNER JOIN user_roles ON user_roles.user_id = users.id ORDER BY name, email", new MyRowMapper());
    }
}