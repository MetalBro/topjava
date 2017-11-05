package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Андрей on 04.11.2017.
 */
public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        req.setCharacterEncoding("UTF-8");

        List<MealWithExceed> mealWithExceededList = MealsUtil.getWithExceeded(MealsUtil.getMeals(), 2000);
        req.setAttribute("mealWithExceededList", mealWithExceededList);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        req.setAttribute("dateTimeFormatter", dateTimeFormatter);

        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action.equals("add")){
            LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("dateTime"));
            String description = req.getParameter("description");
            int calories = Integer.parseInt(req.getParameter("calories"));
            Meal meal = new Meal(localDateTime, description, calories);
            MealsUtil.getMeals().add(meal);
        } else if (action.equals("edit")){
            List<Meal> meals = MealsUtil.getMeals();
            int id = Integer.parseInt(req.getParameter("id"));
            int i = 0;
            while (true){
                Meal meal = meals.get(i);
                if (meal.getId() == id) break;
                i++;
            }
            LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("dateTime"));
            String description = req.getParameter("description");
            int calories = Integer.parseInt(req.getParameter("calories"));
            Meal meal = meals.get(i);
            meal.setDateTime(localDateTime);
            meal.setDescription(description);
            meal.setCalories(calories);
            meals.set(i, meal);
        } else if (action.equals("delete")){
            List<Meal> meals = MealsUtil.getMeals();
            int id = Integer.parseInt(req.getParameter("deleteMealId"));
            int i = 0;
            while (true){
                Meal meal = meals.get(i);
                if (meal.getId() == id) break;
                i++;
            }
            meals.remove(i);
        }
        req.getRequestDispatcher("/index.html").forward(req, resp);
    }
}
