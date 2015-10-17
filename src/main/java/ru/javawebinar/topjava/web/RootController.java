package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
    private static final LoggerWrapper MEAL_LOG = LoggerWrapper.get(MealServlet.class);

    @Autowired
    private UserMealRestController mealController;

    @Autowired
    private UserService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("userList", service.getAll());
        return "userList";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        LoggedUser.setId(userId);
        return "redirect:meals";
    }

    /*=======================for index ============================*/
    @RequestMapping(value = "/index")
    public void index() {
        MEAL_LOG.info("back home");
        root();
    }
    /*=======================for meals ============================*/
    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public ModelAndView getMealList() {
        MEAL_LOG.info("getAll");
        ModelAndView modelAndView = new ModelAndView("mealList");
        modelAndView.addObject("mealList", mealController.getAll());
        return modelAndView;
    }

    @RequestMapping(value = "/meals", params = "action=delete", method = RequestMethod.GET)
    public String deleteMeal(@RequestParam("id") int id) {
        MEAL_LOG.info("Delete {}", id);
        mealController.delete(id);
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", params = "action=update", method = RequestMethod.GET)
    public ModelAndView updateMeal(@RequestParam(value = "id", required = false) int id) {
        final UserMeal meal = mealController.get(id);
        ModelAndView modelAndView = new ModelAndView("mealEdit");
        modelAndView.addObject("meal", meal);
        return modelAndView;
    }

    @RequestMapping(value = "/meals", params = "action=create", method = RequestMethod.GET)
    public ModelAndView createMeal() {
        final UserMeal meal = new UserMeal(LocalDateTime.now(), "", 1000);
        ModelAndView modelAndView = new ModelAndView("mealEdit");
        modelAndView.addObject("meal", meal);
        return modelAndView;
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String createOrUpdateMeal(@RequestParam(value = "id", required = false) String id,
                                     @RequestParam("dateTime") String dateTime,
                                     @RequestParam("description") String description,
                                     @RequestParam("calories") int calories
    ) {
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(dateTime),
                description,
                calories);
        MEAL_LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
        mealController.create(userMeal);
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", params = "action=filter", method = RequestMethod.POST)
    public ModelAndView filterMeal(@RequestParam("startDate") String startDate,
                                   @RequestParam("endDate") String endDate,
                                   @RequestParam("startTime") String startTime,
                                   @RequestParam("endTime") String endTime
    ) {
        ModelAndView modelAndView = new ModelAndView("mealList");
        modelAndView.addObject("mealList", mealController.getBetween(
                TimeUtil.parseLocalDate(startDate, TimeUtil.MIN_DATE),
                TimeUtil.parseLocalTime(startTime, LocalTime.MIN),
                TimeUtil.parseLocalDate(endDate, TimeUtil.MAX_DATE),
                TimeUtil.parseLocalTime(endTime, LocalTime.MAX)));
        return modelAndView;
    }


}
