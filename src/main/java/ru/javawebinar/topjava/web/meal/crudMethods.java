package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public interface crudMethods {

    public Logger getLogger();

    public MealService getService();

    public default int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    public default Meal get(int id) {
        int userId = AuthorizedUser.id();
        getLogger().info("get meal {} for user {}", id, userId);
        return getService().get(id, userId);
    }

    public default void delete(int id) {
        int userId = AuthorizedUser.id();
        getLogger().info("delete meal {} for user {}", id, userId);
        getService().delete(id, userId);
    }

    public default List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.id();
        getLogger().info("getAll for user {}", userId);
        return MealsUtil.getWithExceeded(getService().getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }

    public default Meal create(Meal meal) {
        int userId = AuthorizedUser.id();
        checkNew(meal);
        getLogger().info("create {} for user {}", meal, userId);
        return getService().create(meal, userId);
    }

    public default void update(Meal meal, int id) {
        int userId = AuthorizedUser.id();
        assureIdConsistent(meal, id);
        getLogger().info("update {} for user {}", meal, userId);
        getService().update(meal, userId);
    }

    /**
     * <ol>Filter separately
     * <li>by date</li>
     * <li>by time for every date</li>
     * </ol>
     */
    public default List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = AuthorizedUser.id();
        getLogger().info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Meal> mealsDateFiltered = getService().getBetweenDates(
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId);

        return MealsUtil.getFilteredWithExceeded(mealsDateFiltered,
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        );
    }
}
