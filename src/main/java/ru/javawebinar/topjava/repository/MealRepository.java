package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {

    Meal save(Meal meal);

    // false if not found or wrong user
    boolean delete(int id, int userID);

    // null if not found or wrong user
    Meal get(int id, int userID);

    Collection<Meal> getAll();

    List<Meal> getByUserID(int userId);

    List<Meal> getByUserIDandTime(int userId, LocalDate localDateStart, LocalDate localDateEnd, LocalTime localTimeStart, LocalTime localTimeEnd);
}
