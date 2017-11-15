package ru.javawebinar.topjava.to;

import java.time.LocalDateTime;

public class MealWithExceed {
    private final Integer userId;

    private final Integer id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public MealWithExceed(Integer userId, Integer id, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.userId = userId;
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }

//    @Override
//    public String toString() {
//        return "MealWithExceed{" +
//                "id=" + id +
//                ", dateTime=" + dateTime +
//                ", description='" + description + '\'' +
//                ", calories=" + calories +
//                ", exceed=" + exceed +
//                '}';
//    }


    @Override
    public String toString() {
        return "MealWithExceed{" +
                "userId=" + userId +
                ", id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}