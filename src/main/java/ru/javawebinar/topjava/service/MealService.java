package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface MealService {

    Meal create(Meal meal);

    void update(Meal meal);

//    // false if not found or wrong user
//    boolean delete(int id, int userID);
    void delete(int id, int userID);

    // null if not found or wrong user
    Meal get(int id, int userID);

    Collection<Meal> getAll();

    List<Meal> getByUserID(int userId);

}