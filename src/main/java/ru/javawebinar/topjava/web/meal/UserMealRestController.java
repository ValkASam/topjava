package ru.javawebinar.topjava.web.meal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping(UserMealRestController.REST_URL)
public class UserMealRestController extends AbstractUserMealController {
    public static final String REST_URL = "/rest/meals";

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserMeal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll() {
        return super.getAll();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody UserMeal meal) {
        super.update(meal);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMeal> createWithLocation(@RequestBody UserMeal meal) {
        UserMeal createdMeal = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")  //местонахождение созданного ресурса
                .buildAndExpand(createdMeal.getId())
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriOfNewResource);
        return new ResponseEntity<>(createdMeal, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    /*public List<UserMealWithExceed> getBetween(@RequestParam("startDate") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate startDate,
                                               @RequestParam("startTime") @DateTimeFormat(iso= DateTimeFormat.ISO.TIME)  LocalTime startTime,
                                               @RequestParam("endDate") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)  LocalDate endDate,
                                               @RequestParam("endTime") @DateTimeFormat(iso= DateTimeFormat.ISO.TIME) LocalTime endTime) {*/
    public List<UserMealWithExceed> getBetween(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                               @RequestParam(value = "startTime", required = false) LocalTime startTime,
                                               @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                               @RequestParam(value = "endTime", required = false) LocalTime endTime) {
        return super.getBetween(
                startDate == null ? TimeUtil.MIN_DATE : startDate,
                startTime == null ? LocalTime.MIN : startTime,
                endDate == null ? TimeUtil.MAX_DATE : endDate,
                endTime == null ? LocalTime.MAX : endTime);
    }
}