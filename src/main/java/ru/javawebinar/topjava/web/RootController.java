package ru.javawebinar.topjava.web;

import com.sun.deploy.net.HttpResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.web.user.AbstractUserController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.spi.http.HttpContext;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController extends AbstractUserController {

    private ThreadLocal<ErrorInfo> error = new ThreadLocal<>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "redirect:meals";
    }

    //    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList() {
        return "userList";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String mealList() {
        return "mealList";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model,
                        @RequestParam(value = "error", required = false) boolean error,
                        @RequestParam(value = "message", required = false) String message) {

        model.put("error", error);
        model.put("message", message);
        return "login";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {
        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "profile";
        } else {
            status.setComplete();
            LoggedUser.get().update(userTo);
            super.update(userTo);
            return "redirect:meals";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(ModelMap model, HttpServletRequest req) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "profile";
        } else {
            status.setComplete();
            super.create(UserUtil.createFromTo(userTo));
            return "redirect:login?message=app.registered";
        }
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409 //можно без этого, но так понятней в браузере
    @ExceptionHandler(DataIntegrityViolationException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public String conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        //тут нет возможности использовать model, поэтому не могу внести в нее изменения и отправить на парсинг jsp
        error.set(LOG.getErrorInfo(req.getRequestURL(), e));
        return "forward:registererror";
    }

    @RequestMapping(value = "/registererror", method = RequestMethod.POST)
    public String errorRegister(UserTo userTo, Model model) {
        userTo.setEmail(""); //пока ходим на сервере, параметры сохраняются и можем их переиспользовать
        model.addAttribute("userTo", userTo);
        model.addAttribute("register", true);
        model.addAttribute("errorinfo", "<br>"+error.get().cause+"<br>"+error.get().detail);
        return "profile";
    }

}
