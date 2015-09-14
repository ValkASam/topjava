package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.Profile;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.ProfileServiceImpl;
import ru.javawebinar.topjava.util.UserMealSerializer;

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
public class ProfileServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(ProfileServlet.class);
    private static ProfileServiceImpl profileServiceImpl = new ProfileServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug(request.getRequestURI());
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        switch (path) {
            case "/getprofile": { //данные profile'а по id для заполнения формы редактирования
                LOG.debug("GET request Profile data");
                break;
            }
            case "/": {
                LOG.debug("redirect to profile");
                request.setAttribute("profile", profileServiceImpl.read(1L));
                request.getRequestDispatcher("/view/profile.jsp").forward(request, response);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug(request.getRequestURI());
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        switch (path) {
            case "/update": { //обноление
                LOG.debug("POST update UserMeal data");
                Map<String, String[]> params = (HashMap<String, String[]>) request.getParameterMap();
                Profile profile = new Profile(params.get("name")[0], params.get("email")[0], params.get("password")[0], Integer.parseInt(params.get("caloriesPerDay")[0]));
                profile.setId(Long.parseLong(params.get("id")[0]));
                ServletUtil.returnResult(
                        request,
                        response,
                        null,
                        profileServiceImpl.update(profile));
                break;
            }
            case "/delete": { //удаление
                LOG.debug("POST delete UserMeal");
                break;
            }
            case "/create": { //создание нового
                LOG.debug("POST create UserMeal");
                break;
            }
        }
    }
}
