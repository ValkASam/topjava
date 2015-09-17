package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.annotation.ControllerMethodMapping;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.annotation.ControllerMethodParamMapping;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealServiceImpl;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(UserMealRestController.class);

    //@Autowired
    UserMealServiceImpl service = new UserMealServiceImpl();

    public UserMealRestController() {
        LOG.debug("is created");
    }

    @ControllerMethodMapping(methodName ="GET", path="update")
     public void update(@ControllerMethodParamMapping("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserMeal userMeal = service.get(Integer.valueOf(id));
        if (request != null) {
            request.setAttribute("meal", userMeal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
        LOG.debug("Start updating ... ");
    }

    @ControllerMethodMapping(methodName ="GET", path="create")
    public void create (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer userId = LoggedUser.id();
        if (request != null) {
            request.setAttribute("meal", new UserMeal(LocalDateTime.now(), "", 1000, userId));
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
        LOG.debug("Start creating ... ");
    }

    @ControllerMethodMapping(methodName ="GET", path="delete")
    public void delete(@ControllerMethodParamMapping("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.delete(Integer.valueOf(id));
        if (request != null) response.sendRedirect("meals");
        LOG.info("Delete {}", id);
    }

    public UserMeal get(String id, HttpServletRequest request, HttpServletResponse response) {
        return service.get(Integer.valueOf(id));
    }

    @ControllerMethodMapping(methodName ="GET", path="/")
    public void mealList (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = LoggedUser.id();
        List<UserMeal> list = service.getAll()
                .stream()
                .filter(um->um.getUserId()==userId)
                .collect(Collectors.toList()); //тут отсекаю чужих юзеров - дальше работаем только по своему (залогиненному)
        List<UserMealWithExceed> userMealList =  UserMealsUtil.getWithExceeded(list, 2000);
        if (request != null) {
            request.setAttribute("mealList", userMealList);
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        }
        LOG.info("getAll");
    }

    @ControllerMethodMapping(methodName ="POST", path="save")
    public void save(@ControllerMethodParamMapping("id") String id,
                     @ControllerMethodParamMapping("dateTime") String dateTime,
                     @ControllerMethodParamMapping("description") String description,
                     @ControllerMethodParamMapping("calories") String calories,
                     HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //в mealEdit.jsp подправил на <form method="post" action="meals?action='save'">
        Integer userId = LoggedUser.id();
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(dateTime),
                description,
                Integer.valueOf(calories),
                userId);
        boolean isNew = userMeal.isNew(); //после service.save  userMeal.isNew() станет false, а я хочу LOG писать после завершения операции
        service.save(userMeal);
        LOG.info(isNew ? "Create {}" : "Update {}", userMeal);
        if (request != null) response.sendRedirect("meals");
    }
}
