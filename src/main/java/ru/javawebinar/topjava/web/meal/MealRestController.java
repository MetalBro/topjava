package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public void setService(MealService service) {   // for test
        this.service = service;
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        ValidationUtil.checkNew(meal);
        return service.create(meal);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal);
    }

    public void delete(int id, int userID) throws NotFoundException {
        log.info("delete meal with id={} and userId={}", id, userID);
        service.delete(id, userID);
    }

    public Meal get(int id, int userID) throws NotFoundException {
        log.info("get meal with id={} and userId={}", id, userID);
        return service.get(id, userID);
    }

    public Collection<Meal> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public List<Meal> getByUserID(int userId) {
        log.info("get mealList by userID={}", userId);
        return service.getByUserID(userId);
    }

    public List<MealWithExceed> convertFromMeal(List<Meal> mealList){
        return MealsUtil.getWithExceeded(mealList, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}