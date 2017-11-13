package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userID) {
        Meal meal = get(id, userID);
        return (meal != null) && (repository.remove(id) != null);
    }

    @Override
    public Meal get(int id, int userID) {
        Meal meal = repository.get(id);
        return meal != null ? (meal.getUserId().equals(userID) ? meal : null) : null;
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }

    @Override
    public List<Meal> getByUserID(int userId) {

        List<Meal> mealList = getAll().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .collect(Collectors.toList());
        mealList.sort((Meal m1, Meal m2) -> (m2.getDateTime().compareTo(m1.getDateTime())));
        return mealList;
    }
}