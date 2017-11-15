package ru.javawebinar.topjava.repository.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        MealsUtil.MEALS.forEach(meal -> save(meal, (int) (Math.random()*2) + 1));  // вызов методов сохранения с рандомными userId
    }

    @Override
    public Meal save(Meal meal, int userId) {
//        if (meal.getUserId() != userId) return null;
        if (meal.getUserId() == null) meal.setUserId(userId);
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
        if (meal != null && meal.getUserId() == null) return null;
        else return meal != null ? (meal.getUserId().equals(userID) ? meal : null) : null;
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }

    @Override
    public List<Meal> getByUserID(int userId) {
        List<Meal> mealList = getFilteredByUserId(userId);
        mealList.sort((Meal m1, Meal m2) -> (m2.getDateTime().compareTo(m1.getDateTime())));
        return mealList;
    }

    @Override
    public List<Meal> getByUserIDandTime(int userId, LocalDate localDateStart, LocalDate localDateEnd, LocalTime localTimeStart, LocalTime localTimeEnd) { // доп. метод с оценкой по дате

        LocalDateTime localDateTimeStart = LocalDateTime.of(localDateStart, localTimeStart); // версия без проверки на null
        LocalDateTime localDateTimeEnd = LocalDateTime.of(localDateEnd, localTimeEnd);

        List<Meal> mealList = getFilteredByUserId(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenDate(meal.getDateTime(), localDateTimeStart, localDateTimeEnd))
                .collect(Collectors.toList());
        mealList.sort((Meal m1, Meal m2) -> (m2.getDateTime().compareTo(m1.getDateTime())));
        return mealList;
    }

    private List<Meal> getFilteredByUserId(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

}