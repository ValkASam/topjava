package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.base.UserMealBase;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealServiceImpl;
import ru.javawebinar.topjava.util.UserMealSerializer;
import ru.javawebinar.topjava.util.UserMealWithExceedSerializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MealServlet.class);
    private static UserMealServiceImpl userMealServiceImpl = new UserMealServiceImpl();
    {
        LOG.debug("init dataBase");
        UserMealBase.initBase();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug(request.getRequestURI());
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        switch (path) {
            case "/getusermeal": { //данные UserMeal'а по id для заполнения формы редактирования
                LOG.debug("GET request UserMeal data");
                ServletUtil.returnResult(
                        request,
                        response,
                        new UserMealSerializer(),
                        userMealServiceImpl.read(Long.parseLong(request.getParameter("id"))));
                break;
            }
            case "/loaddata": { //загрузка данных с учетом фильтра
                LOG.debug("GET load UserMeals");
                ServletUtil.returnResult(
                        request,
                        response,
                        new UserMealWithExceedSerializer(),
                        userMealServiceImpl.read((HashMap<String, String[]>) request.getParameterMap()));
                break;
            }
            case "/": {
                LOG.debug("redirect to mealList");
                request.setAttribute("meals", userMealServiceImpl.read());
                request.getRequestDispatcher("/view/mealList.jsp").forward(request, response);
                break;
            }
        }
        //response.sendRedirect("view/mealList.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug(request.getRequestURI());
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        switch (path) {
            case "/update": { //обноление
                LOG.debug("POST update UserMeal data");
                Map<String, String[]> params = (HashMap<String, String[]>) request.getParameterMap();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                UserMeal userMeal = new UserMeal(LocalDateTime.parse(params.get("dateTime")[0], formatter), params.get("description")[0], Integer.parseInt(params.get("calories")[0]));
                userMeal.setId(Long.parseLong(params.get("id")[0]));
                ServletUtil.returnResult(
                        request,
                        response,
                        new UserMealSerializer(),
                        userMealServiceImpl.update(userMeal));
                break;
            }
            case "/delete": { //удаление
                LOG.debug("POST delete UserMeal");
                ServletUtil.returnResult(
                        request,
                        response,
                        null,
                        userMealServiceImpl.delete(Long.parseLong(request.getParameter("id"))));
                break;
            }
            case "/create": { //создание нового
                LOG.debug("POST create UserMeal");
                Map<String, String[]> params = (HashMap<String, String[]>) request.getParameterMap();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                UserMeal userMeal = new UserMeal(LocalDateTime.parse(params.get("dateTime")[0], formatter), params.get("description")[0], Integer.parseInt(params.get("calories")[0]));
                userMeal.setId(Long.parseLong(params.get("id")[0]));
                ServletUtil.returnResult(
                        request,
                        response,
                        new UserMealSerializer(),
                        userMealServiceImpl.create(userMeal));
                break;
            }
        }
    }
}


//request.getRequestDispatcher("/userList.jsp").forward(request, response);
