package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            System.out.println(adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN)));
            //
            UserMealRestController userMealRestController = appCtx.getBean(UserMealRestController.class);
            userMealRestController.delete("2", null, null);
            userMealRestController.create(null, null);
            userMealRestController.save("", LocalDateTime.now().toString(), "", "1000", null, null);
            userMealRestController.update("4", null, null);
            userMealRestController.save("4", LocalDateTime.now().toString(), "", "555", null, null);
            userMealRestController.mealList(null, null);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
