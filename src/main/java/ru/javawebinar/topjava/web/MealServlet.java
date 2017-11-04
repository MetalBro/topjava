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
}
