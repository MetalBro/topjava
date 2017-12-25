package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.Meal;

public class DTO_1 {

    private Meal meal;

    private boolean canVote;

    public DTO_1(Meal meal, boolean canVote) {
        this.meal = meal;
        this.canVote = canVote;
    }
}
