package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void update(Meal meal, int userId) {
        repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userID) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id, userID), id);
    }

    @Override
    public Meal get(int id, int userID) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id, userID), id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Meal> getByUserID(int userId) {
        return repository.getByUserID(userId);
    }

    @Override
    public List<Meal> getByUserIDandTime(int userId, LocalDate localDateStart, LocalDate localDateEnd, LocalTime localTimeStart, LocalTime localTimeEnd) {
        return repository.getByUserIDandTime(userId, localDateStart, localDateEnd, localTimeStart, localTimeEnd);
    }
}