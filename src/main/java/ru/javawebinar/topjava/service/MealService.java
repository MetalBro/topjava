package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealService {

    Meal create(Meal meal, int userId);

    void update(Meal meal, int userId);

//    // false if not found or wrong user
//    boolean delete(int id, int userID);
    void delete(int id, int userID);

    // null if not found or wrong user
    Meal get(int id, int userID);

    Collection<Meal> getAll();

    List<Meal> getByUserID(int userId);

    List<Meal> getByUserIDandTime(int userId, LocalDate localDateStart, LocalDate localDateEnd, LocalTime localTimeStart, LocalTime localTimeEnd);

}