package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.annotation.ControllerMethodMapping;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.annotation.ControllerMethodParamMapping;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.mock.InMemoryUserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MealServlet.class);
    private UserMealRestController userMealRestController = new UserMealRestController();
    private InMemoryUserMealRepository repository;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("has got request");
        req.setCharacterEncoding("UTF-8");
        String methodName = req.getMethod();
        String path = req.getParameter("action") == null ? "/" : req.getParameter("action");
        for (Method controllerMethod : UserMealRestController.class.getDeclaredMethods()) { //смотрим все методы UserMealRestController
            for (Annotation annotation : controllerMethod.getAnnotations()) { //смотрим аннотации каждого метода UserMealRestController
                if (annotation instanceof ControllerMethodMapping) { //если метод аннотирован @ControllerMethodMapping
                    if ((((ControllerMethodMapping) annotation).methodName().equals(methodName))
                            && ((ControllerMethodMapping) annotation).path().equals(path)) { //... и если тип запроса и команда совпадают с таковыми в аннотаци метода
                        try {
                            LOG.debug("has maped request on " + controllerMethod.getName()); //... то это наш метод
                            List<Object> params = new ArrayList<>();
                            for (Parameter parameter : controllerMethod.getParameters()) { //смотрим параметры метода
                                if (parameter.isAnnotationPresent(ControllerMethodParamMapping.class)) { //интересуют аннотированные @ControllerMethodParamMapping параметры
                                    String requestParamName = parameter.getAnnotation(ControllerMethodParamMapping.class).value();
                                    if (req.getParameter(requestParamName) != null) {
                                        params.add(req.getParameter(requestParamName));
                                    } else {
                                        LOG.debug("param "+requestParamName+" in method "+getMethodSignature(controllerMethod)+" is absent");
                                        return;
                                    }
                                }
                            }
                            LOG.debug("has passed request to " + getMethodSignature(controllerMethod));
                            params.add(req); //эти параметры понадобятся в контроллере для формирования представления
                            params.add(resp);
                            controllerMethod.invoke(userMealRestController, params.toArray());
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private String getMethodSignature(Method controllerMethod) {
        StringBuilder msg = new StringBuilder()
                .append(controllerMethod.getName())
                .append("(");
        for (Parameter par : controllerMethod.getParameters())
            msg.append(par.getType().getSimpleName()).append(" ").append(par.getName()).append(", ");
        msg.append(")");
        return msg.toString();
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
