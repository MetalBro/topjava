package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesDay = mealList.stream()
                .collect(Collectors.groupingBy(temp -> temp.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(x -> TimeUtil.isBetween(x.getDateTime().toLocalTime(), startTime, endTime))
                .map(temp -> {
                    LocalDate localDate = temp.getDateTime().toLocalDate();
                    boolean exceed = caloriesDay.get(localDate) > caloriesPerDay;
                    return new UserMealWithExceed(temp.getDateTime(), temp.getDescription(), temp.getCalories(), exceed);
                }).collect(Collectors.toList());
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded_2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesDay = new HashMap<>();
        for (UserMeal userMeal : mealList){
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
//            if (!caloriesDay.containsKey(localDate)) caloriesDay.put(localDate, userMeal.getCalories());
//            else caloriesDay.put(localDate, caloriesDay.get(localDate) + userMeal.getCalories());
            sumCaloriesDay.put(localDate, userMeal.getCalories() + sumCaloriesDay.getOrDefault(localDate, 0));
        }

        List<UserMealWithExceed> list = new ArrayList<>();
        for (UserMeal userMeal : mealList){
            LocalTime localTime = userMeal.getDateTime().toLocalTime();
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            if (TimeUtil.isBetween(localTime, startTime, endTime)){
                boolean exceed = sumCaloriesDay.get(localDate) > caloriesPerDay;
                list.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed));
            }
        }
        return list;
    }
}