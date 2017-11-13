package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            System.out.println();
            System.out.println();
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.setService(new MealServiceImpl(new InMemoryMealRepositoryImpl()));
            List<MealWithExceed> mealWithExceedsId4 = mealRestController.convertToWithExceeded(mealRestController.getByUserID(4));
            List<MealWithExceed> mealWithExceedsId3 = mealRestController.convertToWithExceeded(mealRestController.getByUserID(3));
            List<MealWithExceed> mealWithExceedsId5 = mealRestController.convertToWithExceeded(mealRestController.getByUserID(5));
            List<MealWithExceed> all = mealRestController.convertToWithExceeded(new ArrayList<>(mealRestController.getAll()));
            List<Meal> allMeals = new ArrayList<>(mealRestController.getAll());
            mealWithExceedsId4.forEach(meal -> System.out.println(meal));
            System.out.println();
            mealWithExceedsId3.forEach(meal -> System.out.println(meal));
            System.out.println();
            mealWithExceedsId5.forEach(meal -> System.out.println(meal));
            System.out.println();
            all.forEach(meal -> System.out.println(meal));
            allMeals.forEach(meal -> System.out.println(meal));
        }
    }
}
