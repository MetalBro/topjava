package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import java.time.LocalDate;
import java.time.LocalTime;
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
        int userId = AuthorizedUser.id();
        ValidationUtil.checkNew(meal);
        return service.create(meal, userId);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        int userId = AuthorizedUser.id();
        if (meal.getUserId() != userId) return;
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

//    public void delete(int id, int userID) throws NotFoundException {
    public void delete(int id) throws NotFoundException {
        int userId = AuthorizedUser.id();
        log.info("delete meal with id={} and userId={}", id, userId);
        service.delete(id, userId);
    }

//    public Meal get(int id, int userID) throws NotFoundException {
    public Meal get(int id) throws NotFoundException {
        int userId = AuthorizedUser.id();
        log.info("get meal with id={} and userId={}", id, userId);
        return service.get(id, userId);
    }

    public Collection<Meal> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public List<Meal> getByUserID(int userId) { // для теста передаю userId в SpringMain
//    public List<Meal> getByUserID() {
//        int userId = AuthorizedUser.id();
        log.info("get mealList by userID={}", userId);
        return service.getByUserID(userId);
    }

//    List<Meal> getByUserIDandTime(int userId, LocalDate localDateStart, LocalDate localDateEnd, LocalTime localTimeStart, LocalTime localTimeEnd){
    public List<Meal> getByUserIDandTime(LocalDate localDateStart, LocalDate localDateEnd, LocalTime localTimeStart, LocalTime localTimeEnd){
        int userId = AuthorizedUser.id();
        log.info("get mealList by userID={} and date from {} to {}, time from {} to {}", userId, localDateStart, localDateEnd, localTimeStart, localTimeEnd);
        return service.getByUserIDandTime(userId, localDateStart, localDateEnd, localTimeStart, localTimeEnd);
    }

    public List<MealWithExceed> convertToWithExceeded(List<Meal> mealList){
        return MealsUtil.getWithExceeded(mealList, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}