package ru.javawebinar.topjava.web.user;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

/**
 * User: javawebinar.topjava
 */
@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<User> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void update(@RequestParam("id") int id,
                       @RequestParam("name") String name,
                       @RequestParam("email") String email,
                       @RequestParam("password") String password,
                       @RequestParam(value = "registered", required = false) Date registered,
                       @RequestParam(value = "enabled", required = false) boolean enabled,
                       @RequestParam(value = "caloriesPerDay", required = false) String caloriesPerDay)
    {
        User user = new User(id, name, email, password, Role.ROLE_USER);
        user.setEnabled(enabled);
        if (id == 0) {
            super.create(user);
        } else {
            super.update(user, id);
        }
    }
}
