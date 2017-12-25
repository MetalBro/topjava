package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {

    static final String REST_URL = "/rest/meals";

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @GetMapping(value = "/test/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DTO_1 getDTO(@PathVariable("id") int id) {
        Meal meal = super.get(id);
        boolean canVote = meal.getId() > 100002 ? true : false;
        DTO_1 dto_1 = new DTO_1(meal, canVote);
        return dto_1;
    }

    //curl -X DELETE "localhost:8080/topjava/rest/meals/100010"
    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    //curl -H "Content-Type: application/json" -d '{"description":"novii meal","dateTime":"2011-12-03T10:15:30","calories":"2500"}' "localhost:8080/topjava/rest/meals"
    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal create(@RequestBody Meal meal) {
        return super.create(meal);
    }

    //curl -X PUT -H "Content-Type: application/json" -d '{"description":"novaya eda","dateTime":"2012-12-03T10:15:30","calories":"2500"}' "localhost:8080/topjava/rest/meals/100005"
    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal,@PathVariable("id") int id) {
        super.update(meal, id);
    }

//    @Override
//    @PostMapping(value = "/filter")
//    public List<MealWithExceed> getBetween(@RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
//                                           @RequestParam("startTime") @DateTimeFormat(iso = ISO.TIME)LocalTime startTime,
//                                           @RequestParam("endDate") @DateTimeFormat(iso = ISO.DATE)LocalDate endDate,
//                                           @RequestParam("endTime") @DateTimeFormat(iso = ISO.TIME)LocalTime endTime) {
////    public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
//        return super.getBetween(startDate, startTime, endDate, endTime);
//    }

    //curl -d "startDateTime=2015-05-30T11:00&endDateTime=2015-05-31T19:00" localhost:8080/topjava/rest/meals/filter
    @PostMapping(value = "/filter")
    public List<MealWithExceed> getBetween(@RequestParam("startDateTime") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime startDateTime,
                                           @RequestParam("endDateTime") @DateTimeFormat(iso = ISO.DATE_TIME)LocalDateTime endDateTime) {
        return super.getBetween(startDateTime.toLocalDate(), startDateTime.toLocalTime(), endDateTime.toLocalDate(), endDateTime.toLocalTime());
    }
}