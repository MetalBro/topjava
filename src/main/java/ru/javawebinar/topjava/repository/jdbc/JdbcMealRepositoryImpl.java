package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("datetime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());
        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            int mealId = newKey.intValue();
            meal.setId(mealId);
            jdbcTemplate.update("INSERT INTO user_meals(user_id, meal_id) VALUES (?, ?)", userId, mealId);
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE meals SET datetime=:datetime, description=:description, calories=:calories " +
                            "WHERE id=:id", map);
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals USING user_meals " +
                "WHERE meals.id=user_meals.meal_id " +
                "AND user_meals.meal_id=? " +
                "AND user_meals.user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT meals.id, meals.datetime, meals.description, meals.calories " +
                "FROM meals " +
                "INNER JOIN user_meals ON meals.id = user_meals.meal_id " +
                "WHERE meals.id=? AND user_meals.user_id=?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT meals.id, meals.datetime, meals.description, meals.calories " +
                "FROM meals " +
                "INNER JOIN user_meals ON meals.id = user_meals.meal_id " +
                "INNER JOIN users ON user_meals.user_id = users.id " +
                "WHERE user_meals.user_id=? " +
                "ORDER BY datetime DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("SELECT meals.id, meals.datetime, meals.description, meals.calories " +
                "FROM meals " +
                "INNER JOIN user_meals ON meals.id = user_meals.meal_id " +
                "INNER JOIN users ON user_meals.user_id = users.id " +
                "WHERE user_meals.user_id=? " +
                "AND meals.datetime>=? " +
                "AND meals.datetime<?" +
                "ORDER BY datetime DESC", ROW_MAPPER, userId, startDate, endDate);    }
}
