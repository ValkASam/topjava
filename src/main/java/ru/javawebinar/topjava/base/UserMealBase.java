package ru.javawebinar.topjava.base;

import ru.javawebinar.topjava.model.Profile;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Valk on 11.09.15.
 */
public class UserMealBase {
    /* "Таблица" profilov */
    private static List<Profile> profileList = new ArrayList<>(); //на данный полагаем, что используется только один профиль - admin
    /* "Таблица" UserMeal'ов */
    private static final List<UserMeal> mealList = new ArrayList<>();

    public static synchronized void initBase(){
        List<Profile> defaultProfile = Arrays.asList(
                new Profile("Admin", "admin@gmail.com", "admin", 2000)
        );
        List<UserMeal> defaultList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 25, 11, 0), "Завтрак", 100),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 25, 12, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 25, 22, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 26, 11, 0), "Завтрак", 200),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 26, 12, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 26, 22, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 27, 11, 0), "Завтрак", 300),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 27, 12, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 27, 22, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 28, 11, 0), "Завтрак", 400),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 28, 12, 0), "Обед", 1200),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 28, 22, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 29, 9, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 29, 14, 0), "Обед", 1300),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 29, 21, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 30, 20, 0), "Ужин", 500),

                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 25, 11, 0), "Завтрак", 100),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 25, 12, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 25, 22, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 26, 11, 0), "Завтрак", 200),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 26, 12, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 26, 22, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 27, 11, 0), "Завтрак", 300),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 27, 12, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 27, 22, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 28, 11, 0), "Завтрак", 400),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 28, 12, 0), "Обед", 1200),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 28, 22, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 29, 9, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 29, 14, 0), "Обед", 1300),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 29, 21, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 30, 20, 0), "Ужин", 500),

                new UserMeal(LocalDateTime.of(2015, Month.MAY, 25, 11, 0), "Завтрак", 100),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 25, 12, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 25, 22, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 26, 11, 0), "Завтрак", 200),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 26, 12, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 26, 22, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 27, 11, 0), "Завтрак", 300),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 27, 12, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 27, 22, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 28, 11, 0), "Завтрак", 400),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 28, 12, 0), "Обед", 1200),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 28, 22, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 9, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 14, 0), "Обед", 1300),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 21, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        long[] pid={1};
        profileList.clear();
        profileList.addAll(defaultProfile
                .stream()
                .peek(um->um.setId(pid[0]++))
                .collect(Collectors.toList()));

        long[] id={1};
        mealList.clear();
        mealList.addAll(defaultList
                .stream()
                .peek(um->um.setId(id[0]++))
                .collect(Collectors.toList()));
    }

    //
    public static Profile getProfileById(long profileId) {
        return profileList
                .stream()
                .filter(pr->pr.getId()==profileId)
                .findFirst().get();
    }

    public static synchronized Profile putProfile(Profile profile){
        try {
            UserMealBase.profileList.removeIf(um -> um.getId().equals(profile.getId()));
            UserMealBase.profileList.add(profile);
            return profile;
        } catch (Exception e) {
            return null;
        }
    }

    //
    public static List<UserMeal> getMealList() {
        return mealList;
    }

    public static synchronized UserMeal appendUserMeal(UserMeal userMeal) {
        try {
            long nextId = UserMealBase.mealList
                    .stream()
                    .collect(Collectors
                            .maxBy(new Comparator<UserMeal>() {
                                @Override
                                public int compare(UserMeal o1, UserMeal o2) {
                                    return o1.getId().compareTo(o2.getId());
                                }
                            })).get().getId() + 1;
            userMeal.setId(nextId);
            mealList.add(userMeal);
            return userMeal;
        } catch(Exception e){
            return null;
        }
    }

    public static synchronized UserMeal putUserMeal(UserMeal userMeal){
        try {
            UserMealBase.mealList.removeIf(um -> um.getId().equals(userMeal.getId()));
            UserMealBase.mealList.add(userMeal);
            return userMeal;
        } catch (Exception e) {
            return null;
        }
    }

    public static synchronized boolean deleteUserMeal(Long id){
        try {
            return UserMealBase.mealList.removeIf(um -> um.getId().equals(id));
        } catch (Exception e) {
            return false;
        }
    }

}
